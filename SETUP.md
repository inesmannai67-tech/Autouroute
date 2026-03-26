# Setup rapide - Autouroute

## 1. Base de données
1. Ouvrir **phpMyAdmin** : http://localhost/phpmyadmin  
2. Créer une base **autouroute** si elle n'existe pas  
3. Sélectionner `autouroute` → Onglet **SQL**  
4. Copier/coller le contenu de `database_schema.sql` et exécuter  

## 2. XAMPP
- Démarrer **Apache** et **MySQL** dans XAMPP  

## 3. Firewall (pour téléphone)
- Pare-feu Windows → Autoriser une application  
- Cocher **Apache HTTP Server** pour Réseau privé  

## 4. URL de l'API (pour téléphone)
1. Sur le PC : CMD → `ipconfig` → noter l’**adresse IPv4** (ex: 192.168.1.178)  
2. Ouvrir `ApiClient.kt` (dans data/)  
3. Ajouter ou modifier :  
   `BASE_URL = "http://VOTRE_IP/Autouroute-main/api/"`  
   (remplacer VOTRE_IP par l’IPv4, ex: 192.168.1.178)  
4. **Téléphone et PC doivent être sur le même WiFi**  
5. Reconstruire l’app  

## 5. Test
- Navigateur du téléphone : `http://VOTRE_IP/Autouroute-main/api/ping.php`  
- Si la page affiche `{"success":true,...}`, le serveur est accessible  
