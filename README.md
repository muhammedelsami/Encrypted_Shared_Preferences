# EncryptedSharedPreferences Example

This project demonstrates the usage of **EncryptedSharedPreferences** in Android to securely store sensitive data like user credentials, tokens, and personal information. By using AES256-GCM encryption, it ensures that data is stored securely and automatically encrypted/decrypted by the system.

## Features

- Secure data storage using AES256-GCM encryption
- Automatic encryption and decryption of data
- Protects sensitive information like user passwords and personal data

## Requirements

- Android API Level 23 or higher
- `androidx.security:security-crypto` dependency

## Getting Started

### Step 1: Add the Dependency

In your `build.gradle` file, add the following dependency:

```gradle
dependencies {
    implementation 'androidx.security:security-crypto:1.1.0-alpha04'
}
```

### Step 2: Initialize EncryptedSharedPreferences

You can use **EncryptedSharedPreferences** to store and retrieve encrypted data with minimal changes to traditional `SharedPreferences`.

```kotlin
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

// Initialize EncryptedSharedPreferences
val masterKey = MasterKey.Builder(context)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()

val sharedPreferences = EncryptedSharedPreferences.create(
    context,
    "secure_prefs",  // Name of the SharedPreferences file
    masterKey,       // MasterKey for encryption
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,   // Key encryption scheme
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM  // Value encryption scheme
)
```

### Step 3: Store Data Securely

To store sensitive data, use the `SharedPreferences.Editor` to put encrypted data into the `EncryptedSharedPreferences`.

```kotlin
// Store encrypted data
val editor = sharedPreferences.edit()
editor.putString("sensitive_data_key", "This is a sensitive value.")
editor.apply()
```

### Step 4: Retrieve Decrypted Data

To retrieve data, the system automatically decrypts it when calling `getString` or other retrieval methods.

```kotlin
// Retrieve decrypted data
val sensitiveData = sharedPreferences.getString("sensitive_data_key", "Default Value")
println("Decrypted value: $sensitiveData")
```

## Security Considerations

- **EncryptedSharedPreferences** provides a safe way to store sensitive data on the device. It encrypts both the keys and values using industry-standard encryption techniques.
- It ensures data security even in the case of rooted devices or physical data breaches.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
