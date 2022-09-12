package com.dalfredi.bookkeepingapi.payload.channel;

import com.dalfredi.bookkeepingapi.model.Channel;
import lombok.Data;

@Data
public class ChannelResponse {
    private Long id;
    private String shortName;
    private String fullName;
    private String tgUsername;
    private Long ownerId;
    private String ownerUsername;

    public ChannelResponse(Channel channel) {
        this.id = channel.getId();
        this.shortName = channel.getShortName();
        this.fullName = channel.getFullName();
        this.tgUsername = channel.getTgUsername();
        this.ownerId = channel.getOwner().getId();
        this.ownerUsername = channel.getOwner().getUsername();
    }
}
