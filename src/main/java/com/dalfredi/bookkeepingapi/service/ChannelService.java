package com.dalfredi.bookkeepingapi.service;

import static com.dalfredi.bookkeepingapi.utils.Constants.CHANNEL;
import static com.dalfredi.bookkeepingapi.utils.Constants.DELETE;
import static com.dalfredi.bookkeepingapi.utils.Constants.EDIT;
import static com.dalfredi.bookkeepingapi.utils.Constants.ID;
import static com.dalfredi.bookkeepingapi.utils.Constants.SEE;
import static com.dalfredi.bookkeepingapi.utils.Constants.USER;

import com.dalfredi.bookkeepingapi.exception.AccessDeniedException;
import com.dalfredi.bookkeepingapi.exception.ResourceNotFoundException;
import com.dalfredi.bookkeepingapi.model.Channel;
import com.dalfredi.bookkeepingapi.model.User;
import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import com.dalfredi.bookkeepingapi.payload.channel.ChannelRequest;
import com.dalfredi.bookkeepingapi.payload.channel.ChannelResponse;
import com.dalfredi.bookkeepingapi.repository.ChannelRepository;
import com.dalfredi.bookkeepingapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public ChannelResponse addChannel(ChannelRequest channelRequest,
                                      Long ownerId) {
        User user = userRepository.findById(ownerId)
            .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 0L));

        Channel channel = new Channel();
        channel.setShortName(channelRequest.getShortName());
        channel.setFullName(channelRequest.getFullName());
        channel.setTgUsername(channelRequest.getTgUsername());
        channel.setOwner(user);

        Channel createdChannel = channelRepository.save(channel);

        return new ChannelResponse(createdChannel);
    }

    public ChannelResponse updateChannel(Long channelId,
                                         ChannelRequest channelRequest,
                                         Long currentUserId) {
        Channel channel = channelRepository.findById(channelId)
            .orElseThrow(
                () -> new ResourceNotFoundException(CHANNEL, ID, channelId));

        if (channel.getOwner().getId().equals(currentUserId)) {
            channel.setShortName(channelRequest.getShortName());
            channel.setFullName(channelRequest.getFullName());
            channel.setTgUsername(channelRequest.getTgUsername());
            return new ChannelResponse(channelRepository.save(channel));
        }

        throw new AccessDeniedException(EDIT, CHANNEL);
    }

    public ChannelResponse findChannelById(Long channelId, Long currentUserId) {
        Channel channel = channelRepository.findById(channelId)
            .orElseThrow(
                () -> new ResourceNotFoundException(CHANNEL, ID, channelId));
        if (channel.getOwner().getId().equals(currentUserId)) {
            return new ChannelResponse(channel);
        }

        throw new AccessDeniedException(SEE, CHANNEL);
    }

    public ApiResponse deleteChannel(Long channelId, Long currentUserId) {
        Channel channel = channelRepository.findById(channelId)
            .orElseThrow(
                () -> new ResourceNotFoundException(CHANNEL, ID, channelId));
        if (channel.getOwner().getId().equals(currentUserId)) {
            channelRepository.deleteById(channelId);
            return new ApiResponse(Boolean.TRUE,
                "You successfully deleted channel");
        }

        throw new AccessDeniedException(DELETE, CHANNEL);
    }
}
