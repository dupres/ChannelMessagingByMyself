package stephen.dupre.channelmessagingbymyself;

/**
 * Created by dupres on 27/01/2017.
 */
public class Channel {
    //Cf. Cours
    private int channelID;
    private String name;
    private String connectedusers;

    //Classique. No comment

    @Override
    public String toString() {
        return "Channel{" +
                "channelID=" + channelID +
                ", name='" + name + '\'' +
                ", connectedusers=" + connectedusers +
                '}';
    }

    public Channel(String connectedusers, String name, int channelID) {
        this.connectedusers = connectedusers;
        this.name = name;
        this.channelID = channelID;
    }

    public String getConnectedusers() {

        return connectedusers;
    }

    public void setConnectedusers(String connectedusers) {
        this.connectedusers = connectedusers;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
