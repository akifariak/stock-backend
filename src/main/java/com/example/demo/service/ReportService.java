package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private  final ExpenseInvoiceRepository expenseInvoiceRepository;
    private final PurchaseRepository purchaseRepository;
    private final WarehouseTransferRepository warehouseTransferRepository;
    private final ClientRepository clientRepository;
    private final StockRepository stockRepository;

    public ReportService(ExpenseInvoiceRepository expenseInvoiceRepository, PurchaseRepository purchaseRepository, WarehouseTransferRepository warehouseTransferRepository, ClientRepository clientRepository, StockRepository stockRepository) {
        this.expenseInvoiceRepository = expenseInvoiceRepository;
        this.purchaseRepository = purchaseRepository;
        this.warehouseTransferRepository = warehouseTransferRepository;
        this.clientRepository = clientRepository;
        this.stockRepository = stockRepository;
    }

    // Constructor, Dependency Injection kullanılabilir

    public List<Object> getDailyExpenses(LocalDate date) {
        List<Object> dailyExpenses = new ArrayList<>();

        List<ExpenseInvoice> expenseInvoices = expenseInvoiceRepository.getExpenseInvoicesByDate(date);
        for (ExpenseInvoice expense : expenseInvoices) {
            Map<String, Object> expenseMap = new HashMap<>();
            expenseMap.put("expense_id", expense.getExpence_id());
            expenseMap.put("stockName", expense.getStockCode().getStockName());
            expenseMap.put("warehouseName", expense.getStockCode().getWarehouse().getName());
            expenseMap.put("clientName", expense.getClientId().getName());
            expenseMap.put("quantity", expense.getQuantity());
            expenseMap.put("price", expense.getPrice());
            expenseMap.put("date", expense.getDate());
            dailyExpenses.add(expenseMap);
        }

        List<PurchaseInvoice> purchaseInvoices = purchaseRepository.getPurchaseInvoicesByDate(date);
        for (PurchaseInvoice invoice : purchaseInvoices) {
            Map<String, Object> expenseMap = new HashMap<>();
            expenseMap.put("purchase_id", invoice.getPurchase_id());
            expenseMap.put("stockName", invoice.getStockCode().getStockName());
            expenseMap.put("warehouseName", invoice.getStockCode().getWarehouse().getName());
            expenseMap.put("authorized", invoice.getWarehouseId().getAuthorized());
            expenseMap.put("quantity", invoice.getQuantity());
            expenseMap.put("price", invoice.getPrice());
            expenseMap.put("date", invoice.getDate());
            dailyExpenses.add(expenseMap);
        }


        List<WarehouseTransfer> warehouseTransfers = warehouseTransferRepository.getWarehouseTransfersByDate(date);
        for (WarehouseTransfer warehouse : warehouseTransfers) {
            Map<String, Object> expenseMap = new HashMap<>();
            expenseMap.put("warehousetransfer_id", warehouse.getWarehouseTransferId());
            expenseMap.put("approvalstatus", warehouse.getApprovalStatus());
            expenseMap.put("source", warehouse.getSource().getName());
            expenseMap.put("target", warehouse.getTarget().getName());
            expenseMap.put("quantity", warehouse.getQuantity());
            expenseMap.put("date", warehouse.getDate());
            expenseMap.put("comment", warehouse.getComment());
            dailyExpenses.add(expenseMap);
        }

        List<Client> clients = clientRepository.getClientsByRegistrationDate(date);
        for (Client client : clients) {
            Map<String, Object> expenseMap = new HashMap<>();
            expenseMap.put("clientId", client.getClientId());
            expenseMap.put("name", client.getName()+client.getSurname());
            System.out.println(client.getName());
            expenseMap.put("address", client.getAddress());
            expenseMap.put("country", client.getCountry());
            expenseMap.put("city", client.getCity());
            expenseMap.put("phone", client.getPhone());
            expenseMap.put("gsm", client.getGsm());
            expenseMap.put("registrationDate", client.getRegistrationDate());
            dailyExpenses.add(expenseMap);
        }

        List<Stock> stocks = stockRepository.getStocksByRegistrationDate(date);
        for (Stock stock : stocks) {
            Map<String, Object> expenseMap = new HashMap<>();
            expenseMap.put("stockId", stock.getStockId());
            expenseMap.put("stockCode", stock.getStockCode());
            expenseMap.put("stockName", stock.getStockName());
            expenseMap.put("barcode", stock.getBarcode());
            expenseMap.put("groupName", stock.getGroupName());
            expenseMap.put("middleGroupName", stock.getMiddleGroupName());
            expenseMap.put("unit", stock.getUnit());
            expenseMap.put("salesPrice", stock.getSalesPrice());
            expenseMap.put("purchasePrice", stock.getSalesPrice());
            expenseMap.put("registrationDate", stock.getRegistrationDate());

            dailyExpenses.add(expenseMap);
        }

        return dailyExpenses;
    }

//    public List<Object> getDailyExpensesForClient(LocalDate date) {
//        List<Object> dailyExpenses = new ArrayList<>();
//
//        List<Client> clients = clientRepository.getClientsByRegistrationDate(date);
//        for (Client client : clients) {
//            Map<String, Object> expenseMap = new HashMap<>();
//            expenseMap.put("clientId", client.getClientId());
//            expenseMap.put("name", client.getName()+client.getSurname());
//            System.out.println(client.getName());
//            expenseMap.put("address", client.getAddress());
//            expenseMap.put("country", client.getCountry());
//            expenseMap.put("city", client.getCity());
//            expenseMap.put("phone", client.getPhone());
//            expenseMap.put("gsm", client.getGsm());
//            expenseMap.put("registrationDate", client.getRegistrationDate());
//            dailyExpenses.add(expenseMap);
//        }
//        return dailyExpenses;
//    }

//    public List<Object> getDailyExpensesForStock(LocalDate date) {
//        List<Object> dailyExpenses = new ArrayList<>();
//
//        List<Stock> stocks = stockRepository.getStocksByRegistrationDate(date);
//        for (Stock stock : stocks) {
//            Map<String, Object> expenseMap = new HashMap<>();
//            expenseMap.put("stockId", stock.getStockId());
//            expenseMap.put("stockCode", stock.getStockCode());
//            expenseMap.put("stockName", stock.getStockName());
//            expenseMap.put("barcode", stock.getBarcode());
//            expenseMap.put("groupName", stock.getGroupName());
//            expenseMap.put("middleGroupName", stock.getMiddleGroupName());
//            expenseMap.put("unit", stock.getUnit());
//            expenseMap.put("salesPrice", stock.getSalesPrice());
//            expenseMap.put("purchasePrice", stock.getSalesPrice());
//            expenseMap.put("registrationDate", stock.getRegistrationDate());
//
//            dailyExpenses.add(expenseMap);
//        }
//        return dailyExpenses;
//    }

    public List<Object> getWeeklyPurchaseInvoices(LocalDate startDate, LocalDate endDate) {
        List<Object> weekly = new ArrayList<>();

        List<ExpenseInvoice> expenseInvoices = expenseInvoiceRepository.getExpenseInvoicesByDateBetween(startDate,endDate);
        weekly.addAll(expenseInvoices);

        List<PurchaseInvoice> purchaseInvoices = purchaseRepository.getPurchaseInvoicesByDateBetween(startDate,endDate);
        weekly.addAll(purchaseInvoices);

        List<WarehouseTransfer> warehouseTransfers = warehouseTransferRepository.getWarehouseTransfersByDateBetween(startDate,endDate);
        weekly.addAll(warehouseTransfers);

        return weekly;
    }


}