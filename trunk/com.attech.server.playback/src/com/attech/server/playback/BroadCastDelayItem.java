/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import com.attech.adsb.record.RecordItem;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author root
 */
public class BroadCastDelayItem implements Delayed {

    private final long origin;
    private final long delay;
    private BroadCastContentItem item;
    private final long time;

    public BroadCastDelayItem(RecordItem recordItem, final long originStart, final long origin) {
        this.time = recordItem.getTime();
        this.delay = recordItem.getTime() - originStart;
        System.out.println("Delay " + this.delay);
        this.origin = origin;
        this.item = new BroadCastContentItem(recordItem.getBytes());
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delay - (System.currentTimeMillis() - PlayItem.startTime), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        if (delayed == this) return 0;

        if (delayed instanceof BroadCastDelayItem) {
            long diff = delay - ((BroadCastDelayItem) delayed).delay;
            return ((diff == 0) ? 0 : ((diff < 0) ? -1 : 1));
        }

        long d = (getDelay(TimeUnit.MILLISECONDS) - delayed.getDelay(TimeUnit.MILLISECONDS));
        return ((d == 0) ? 0 : ((d < 0) ? -1 : 1));
    }
    
    public boolean isPack(RecordItem recordItem) {
        if (this.time != recordItem.getTime()) return false;
        this.item.add(recordItem.getBytes());
        return true;
    }
    
    public byte[] getByte() {
        return this.item.getByte();
    }
}
