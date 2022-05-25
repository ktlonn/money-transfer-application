package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDetails;
import com.techelevator.util.BasicLogger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public BigDecimal getBalance() {
        Account account = null;
        BigDecimal balance = null;
        try {
            account = restTemplate.exchange(
                    API_BASE_URL + "balance",
                    HttpMethod.GET,
                    makeAuthEntity(),
                    Account.class
            ).getBody();
            balance = account.getBalance();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

    public TransferDetails getTransferDetails(Long transferId) {
        TransferDetails transferDetails = null;
        try{
            transferDetails = restTemplate.exchange(API_BASE_URL + "transfer/" + transferId,
                    HttpMethod.GET,
                    makeAuthEntity(),
                    TransferDetails.class
            ).getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferDetails;
    }

    public List<TransferDetails> getPastTransfers() {
        List<TransferDetails> transferHistory = new ArrayList<>();
        TransferDetails[] transfersArr = null;

        try {
            transfersArr = restTemplate.exchange(
                    API_BASE_URL + "/transfer/history",
                    HttpMethod.GET,
                    makeAuthEntity(),
                    TransferDetails[].class
            ).getBody();

            if (transfersArr != null) {
                for (TransferDetails transferDetails : transfersArr) {
                    transferHistory.add(transferDetails);
                }
            }

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transferHistory;
    }

    public boolean containsTransfer(long transferId) {
        List<TransferDetails> transferList;

        transferList = getPastTransfers();

        for (TransferDetails transferDetails : transferList) {
            if (transferDetails.getTransferId().equals(transferId)) {
                return true;
            }
        }
        return false;
    }

    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<Account> entity = new HttpEntity<>(account, headers);
        return entity;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return entity;
    }

}
