package com.alejandro.microservices.api_wallet.wallet.service;

import com.alejandro.microservices.api_wallet.wallet.entity.User;
import com.alejandro.microservices.api_wallet.wallet.entity.Wallet;
import com.alejandro.microservices.api_wallet.wallet.repository.UserRepository;
import com.alejandro.microservices.api_wallet.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public Wallet createWalletForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (walletRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("El usuario ya tiene wallet");
        }

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .build();

        return walletRepository.save(wallet);
    }

    public Wallet getWalletByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return walletRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet no encontrada"));
    }

    @Transactional
    public void transfer(String fromEmail, String toEmail, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto debe ser mayor a 0");
        }

        User fromUser = userRepository.findByEmail(fromEmail)
                .orElseThrow(() -> new RuntimeException("Usuario origen no encontrado"));

        User toUser = userRepository.findByEmail(toEmail)
                .orElseThrow(() -> new RuntimeException("Usuario destino no encontrado"));

        Wallet fromWallet = walletRepository.findByUser(fromUser)
                .orElseThrow(() -> new RuntimeException("Wallet de origen no encontrada"));

        Wallet toWallet = walletRepository.findByUser(toUser)
                .orElseThrow(() -> new RuntimeException("Wallet de destino no encontrada"));

        if (fromWallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Fondos insuficientes");
        }

        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.setBalance(toWallet.getBalance().add(amount));

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }
}
