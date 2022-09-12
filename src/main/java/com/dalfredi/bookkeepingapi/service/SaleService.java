package com.dalfredi.bookkeepingapi.service;

import static com.dalfredi.bookkeepingapi.utils.Constants.AD_FORMAT;
import static com.dalfredi.bookkeepingapi.utils.Constants.CHANNEL;
import static com.dalfredi.bookkeepingapi.utils.Constants.CUSTOMER;
import static com.dalfredi.bookkeepingapi.utils.Constants.ID;
import static com.dalfredi.bookkeepingapi.utils.Constants.SALE;
import static com.dalfredi.bookkeepingapi.utils.Constants.STATUS;

import com.dalfredi.bookkeepingapi.exception.ResourceNotFoundException;
import com.dalfredi.bookkeepingapi.exception.UnauthorizedException;
import com.dalfredi.bookkeepingapi.model.AdFormat;
import com.dalfredi.bookkeepingapi.model.Channel;
import com.dalfredi.bookkeepingapi.model.Customer;
import com.dalfredi.bookkeepingapi.model.PaymentStatus;
import com.dalfredi.bookkeepingapi.model.Sale;
import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import com.dalfredi.bookkeepingapi.payload.sale.SaleDTO;
import com.dalfredi.bookkeepingapi.payload.sale.SaleRequest;
import com.dalfredi.bookkeepingapi.repository.AdFormatRepository;
import com.dalfredi.bookkeepingapi.repository.ChannelRepository;
import com.dalfredi.bookkeepingapi.repository.CustomerRepository;
import com.dalfredi.bookkeepingapi.repository.SaleRepository;
import com.dalfredi.bookkeepingapi.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final ChannelRepository channelRepository;
    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final StatusRepository statusRepository;
    private final AdFormatRepository formatRepository;

    public SaleDTO addSale(Long channelId, SaleRequest saleRequest,
                           Long userId) {
        Channel channel = channelRepository.findById(channelId)
            .orElseThrow(
                () -> new ResourceNotFoundException(CHANNEL, ID, channelId));
        if (channel.getOwner().getId().equals(userId)) {
            AdFormat format =
                formatRepository.findById(saleRequest.getFormat().getId())
                    .orElseThrow(
                        () -> new ResourceNotFoundException(AD_FORMAT, ID,
                            saleRequest.getFormat().getId()));
            Customer customer =
                customerRepository.findById(saleRequest.getCustomer().getId())
                    .orElseThrow(
                        () -> new ResourceNotFoundException(CUSTOMER, ID,
                            saleRequest.getCustomer().getId()));
            PaymentStatus status =
                statusRepository.findById(saleRequest.getStatus().getId())
                    .orElseThrow(
                        () -> new ResourceNotFoundException(STATUS, ID,
                            saleRequest.getStatus().getId()));
            Sale sale = new Sale();
            sale.setDateTime(saleRequest.getDateTime());
            sale.setFormat(format);
            sale.setCustomer(customer);
            sale.setPrice(saleRequest.getPrice());
            sale.setStatus(status);
            sale.setChannel(channel);
            Sale newSale = saleRepository.save(sale);
            return SaleDTO.of(newSale);
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
            "You don't have permission to add sale to this channel");
        throw new UnauthorizedException(apiResponse);
    }

    public SaleDTO getById(Long channelId, Long saleId, Long userId) {
        Channel channel = channelRepository.findById(channelId)
            .orElseThrow(
                () -> new ResourceNotFoundException(CHANNEL, ID, channelId));
        if (channel.getOwner().getId().equals(userId)) {
            Sale sale = saleRepository.findById(saleId)
                .orElseThrow(
                    () -> new ResourceNotFoundException(SALE, ID, saleId));
            if (sale.getChannel().getId().equals(channelId)) {
                return SaleDTO.of(sale);
            }
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
            "You don't have permission to see this sale info");
        throw new UnauthorizedException(apiResponse);
    }

    public SaleDTO updateSale(Long channelId, Long saleId, SaleDTO saleRequest,
                              Long userId) {
        Channel channel = channelRepository.findById(channelId)
            .orElseThrow(
                () -> new ResourceNotFoundException(CHANNEL, ID, channelId));
        if (channel.getOwner().getId().equals(userId)) {
            AdFormat format =
                formatRepository.findById(saleRequest.getFormat().getId())
                    .orElseThrow(
                        () -> new ResourceNotFoundException(AD_FORMAT, ID,
                            saleRequest.getFormat().getId()));
            Customer customer =
                customerRepository.findById(saleRequest.getCustomer().getId())
                    .orElseThrow(
                        () -> new ResourceNotFoundException(CUSTOMER, ID,
                            saleRequest.getCustomer().getId()));
            PaymentStatus status =
                statusRepository.findById(saleRequest.getStatus().getId())
                    .orElseThrow(
                        () -> new ResourceNotFoundException(STATUS, ID,
                            saleRequest.getStatus().getId()));
            Sale updatedSale = saleRepository.findById(saleId)
                .orElseThrow(
                    () -> new ResourceNotFoundException(SALE, ID, saleId));
            updatedSale.setDateTime(saleRequest.getDateTime());
            updatedSale.setFormat(format);
            updatedSale.setCustomer(customer);
            updatedSale.setPrice(saleRequest.getPrice());
            updatedSale.setStatus(status);
            updatedSale.setChannel(channel);
            Sale newSale = saleRepository.save(updatedSale);
            return SaleDTO.of(newSale);
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
            "You don't have permission to edit this sale info");
        throw new UnauthorizedException(apiResponse);
    }


    public ApiResponse deleteSaleById(Long saleId, Long userId) {
        Sale sale = saleRepository.findById(saleId)
            .orElseThrow(
                () -> new ResourceNotFoundException(SALE, ID, saleId));
        if (sale.getChannel().getOwner().getId().equals(userId)) {
            saleRepository.deleteById(saleId);
            return new ApiResponse(Boolean.TRUE,
                "You successfully deleted sale");
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
            "You don't have permission to delete this sale");
        throw new UnauthorizedException(apiResponse);
    }
}
