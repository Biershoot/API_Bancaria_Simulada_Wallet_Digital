package com.alejandro.microservices.api_wallet.wallet.controller;

import com.alejandro.microservices.api_wallet.wallet.dto.TransferRequest;
import com.alejandro.microservices.api_wallet.wallet.dto.TransferResponse;
import com.alejandro.microservices.api_wallet.wallet.dto.WalletResponse;
import com.alejandro.microservices.api_wallet.wallet.entity.Wallet;
import com.alejandro.microservices.api_wallet.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/create")
    public ResponseEntity<WalletResponse> createWallet(Authentication authentication) {
        Wallet wallet = walletService.createWalletForUser(authentication.getName());
        WalletResponse response = new WalletResponse(
            wallet.getId(), 
            wallet.getUser().getEmail(), 
            wallet.getBalance()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance")
    public ResponseEntity<WalletResponse> getBalance(Authentication authentication) {
        Wallet wallet = walletService.getWalletByUserEmail(authentication.getName());
        WalletResponse response = new WalletResponse(
            wallet.getId(), 
            wallet.getUser().getEmail(), 
            wallet.getBalance()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest request,
                                                     Authentication authentication) {
        walletService.transfer(authentication.getName(), request.getToEmail(), request.getAmount());
        
        TransferResponse response = new TransferResponse(
            "Transferencia realizada con éxito",
            authentication.getName(),
            request.getToEmail(),
            request.getAmount(),
            java.time.Instant.now()
        );
        
        return ResponseEntity.ok(response);
    }
}
