package com.alejandro.microservices.api_wallet.wallet.controller;

import com.alejandro.microservices.api_wallet.wallet.dto.TransferRequest;
import com.alejandro.microservices.api_wallet.wallet.dto.TransferResponse;
import com.alejandro.microservices.api_wallet.wallet.dto.WalletResponse;
import com.alejandro.microservices.api_wallet.wallet.entity.Wallet;
import com.alejandro.microservices.api_wallet.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@Tag(name = "Wallet", description = "Operaciones de wallet digital")
@SecurityRequirement(name = "bearerAuth")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/create")
    @Operation(summary = "Crear wallet", description = "Crea una nueva wallet para el usuario autenticado")
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
    @Operation(summary = "Obtener balance", description = "Obtiene el balance de la wallet del usuario autenticado")
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
    @Operation(summary = "Realizar transferencia", description = "Realiza una transferencia desde la wallet del usuario autenticado a otra wallet")
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
