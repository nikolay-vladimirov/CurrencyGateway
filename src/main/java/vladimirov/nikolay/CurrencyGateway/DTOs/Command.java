package vladimirov.nikolay.CurrencyGateway.DTOs;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement
public class Command {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "get")
    private GetCommand getCommand;

    @JacksonXmlProperty(localName = "history")
    private HistoryCommand historyCommand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public GetCommand getGetCommand() {
        return getCommand;
    }

    public void setGetCommand(GetCommand getCommand) {
        this.getCommand = getCommand;
    }

    public HistoryCommand getHistoryCommand() {
        return historyCommand;
    }

    public void setHistoryCommand(HistoryCommand historyCommand) {
        this.historyCommand = historyCommand;
    }
}
