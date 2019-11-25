package priv.just.framework.curator.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;

import java.util.List;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2019-11-25 19:14
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaderSelectorListenerComposite implements LeaderSelectorListener {

    private List<LeaderSelectorListener> listeners;

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        for (LeaderSelectorListener listener : listeners) {
            try {
                listener.takeLeadership(client);
            } catch (Exception e) {
                log.error("【{}】 takeLeadership error", listener.getClass().getName(), e);
            }
        }
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        for (LeaderSelectorListener listener : listeners) {
            try {
                listener.stateChanged(client, newState);
            } catch (Exception e) {
                log.error("【{}】 stateChanged error", listener.getClass().getName(), e);
            }
        }
    }

    public void addListener(LeaderSelectorListener listener) {
        listeners.add(listener);
    }

    public void removeListener(LeaderSelectorListener listener) {
        listeners.remove(listener);
    }

}
