package jagsc.org.abc.info.event;

/**
 * Created by kinagafuji on 16/03/01.
 */
public class StarClickedEvent {
    //MainActivityからDetailActivityに遷移し、お気に入りの追加・解除を行った際に、
    //その変更をMainActivityにも反映させるために、Eventを送る
    //ライブラリはOttoやEventBusなどがある　最近はRxAndroidでも
    public int position;

    public StarClickedEvent(int position) {
        super();
        this.position = position;
    }
}
