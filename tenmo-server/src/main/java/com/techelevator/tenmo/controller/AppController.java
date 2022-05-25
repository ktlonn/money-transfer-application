package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.TransferDetailsDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class AppController {

    @Autowired
    AccountDAO accountDAO;
    @Autowired
    TransferDao transferDao;
    @Autowired
    TransferDetailsDao transferDetailsDao;
    @Autowired
    UserDao userDao;

    @RequestMapping(path="/balance", method = RequestMethod.GET)
    public Account getBalance(Principal principal) {
        principal.getName();
        int userId = userDao.findIdByUsername(principal.getName());
        return accountDAO.getBalance((long)userId);
    }

    @RequestMapping(path="/tenmo_user", method = RequestMethod.GET)
    public List<User> getUsers() {
        return transferDao.viewListOfUsers();
    }

    @RequestMapping(path="/transfer", method = RequestMethod.POST)
    public Transfer makeTransfer(@RequestBody TransferDTO transferDTO) {
        Transfer transfer = mapDTOToTransfer(transferDTO);
        accountDAO.transferBalance(transfer);
        return transferDao.addTransfer(transfer);
    }

    @RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.GET)
    public TransferDetails getTransferDetails(@PathVariable Long transferId) {
        return transferDetailsDao.getTransferDetails(transferId);
    }

    @RequestMapping(path = "/transfer/history", method = RequestMethod.GET)
    public List<TransferDetails> getTransferHistory(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        long accountId = userDao.findAccountByUserId(userId);

        return transferDetailsDao.getTransferHistory(accountId);
    }

    private Transfer mapDTOToTransfer(TransferDTO transferDTO) {
        Transfer transfer = new Transfer();
        transfer.setTransferTypeId(2); // 2 = send
        transfer.setTransferStatusId(2); // 2 = approved
        transfer.setAccountFromId(userDao.findAccountByUserId(transferDTO.getUserFromId()));
        transfer.setAccountToId(userDao.findAccountByUserId(transferDTO.getUserToId()));
        transfer.setTransferAmount(transferDTO.getTransferAmount());

        return transfer;
    }

}
