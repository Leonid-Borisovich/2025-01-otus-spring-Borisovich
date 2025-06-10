package ru.otus.hw;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCreator {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "alisa";
		String encryptedPassword = encoder.encode(rawPassword);
		System.out.println("Зашифрованный пароль: " + encryptedPassword);
	}

}
