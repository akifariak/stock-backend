package com.example.demo.service;


import com.example.demo.Dto.BalanceDto;
import com.example.demo.model.Balance;
import com.example.demo.repository.BalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public boolean addBalance(BalanceDto balance){
       Balance b = new Balance(balance.getClientID(), balance.getDebitCreditStatus(), balance.getTurnoverDebit(),balance.getTurnoverCredit(),balance.getTurnoverBalance(),balance.getTransactionalDebit(),balance.getTransactionalCredit(),balance.getTransactionalBalance(),balance.getComment());
        balanceRepository.save(b);
       return true;
    }
}

