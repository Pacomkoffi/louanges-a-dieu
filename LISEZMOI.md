# Louanges à Dieu — application Android

**Auteur :** Pak le Bienheureux, à partir du livre de cantiques *Louanges à Dieu*
**Gratuit pour le peuple de Dieu**

934 cantiques, recherche par numéro, titre ou paroles, favoris, récents, taille du texte réglable.
L'application fonctionne **entièrement hors connexion** et ne demande **aucune autorisation**.

---

## Obtenir le fichier APK (une seule fois, environ 30 minutes)

### 1. Créer un compte GitHub
Allez sur **https://github.com/signup** et créez un compte gratuit (adresse e-mail + mot de passe).

### 2. Créer un dépôt (un « dossier » en ligne)
1. Une fois connecté, touchez le **+** en haut à droite, puis **New repository**.
2. **Repository name** : `LouangesADieu`
3. Choisissez **Private** (privé — recommandé, voir « Clé de signature » plus bas).
4. Ne cochez rien d'autre. Cliquez sur **Create repository**.

### 3. Déposer les fichiers
1. Sur la page du dépôt, cliquez sur le lien **uploading an existing file**.
2. Sur votre ordinateur, **décompressez** le fichier `LouangesADieu.zip`.
3. Ouvrez le dossier `LouangesADieu`, sélectionnez **tout son contenu** (Ctrl + A), et faites-le glisser dans la page GitHub.
   ⚠️ Glissez le *contenu* du dossier, pas le dossier lui-même : les fichiers `settings.gradle`, `build.gradle` et le dossier `app` doivent se retrouver directement à la racine du dépôt.
4. Attendez la fin du chargement, puis cliquez sur **Commit changes** en bas de la page.

> **Le dossier `.github` n'apparaît pas ?** Sur certains ordinateurs (Mac notamment), les dossiers qui commencent par un point sont cachés et ne sont pas envoyés.
> Dans ce cas : sur GitHub, cliquez **Add file → Create new file**, tapez comme nom
> `.github/workflows/construire-apk.yml`, collez dans la grande zone le contenu du fichier
> `construire-apk.yml` (ouvrez-le avec le Bloc-notes), puis **Commit changes**.

### 4. Laisser GitHub fabriquer l'APK
1. Cliquez sur l'onglet **Actions** du dépôt.
2. Une construction « Construire l'APK » démarre toute seule (rond jaune ⏳). Si GitHub vous demande d'activer les Actions, acceptez.
3. Après 5 à 10 minutes, le rond devient une **coche verte ✅**.

### 5. Télécharger l'APK
- Sur la page d'accueil du dépôt, à droite, cliquez sur **Releases**, puis sur la dernière version.
- Téléchargez **LouangesADieu.apk**.

---

## Installer sur un téléphone Android
1. Envoyez le fichier `LouangesADieu.apk` sur le téléphone (WhatsApp, câble USB, Google Drive…).
2. Touchez le fichier. Android affiche un avertissement : touchez **Paramètres**, puis activez **Autoriser cette source**.
3. Revenez en arrière, touchez **Installer**.
4. Si **Play Protect** affiche une alerte : **Plus de détails → Installer quand même**. C'est normal pour toute application qui ne vient pas du Play Store.

L'icône **Louanges à Dieu** (épis de blé dorés et croix) apparaît parmi vos applications.

---

## Publier une nouvelle version plus tard
1. Remplacez le fichier `app/src/main/assets/index.html` dans le dépôt (bouton **Add file → Upload files**).
2. GitHub refait l'APK automatiquement. Le numéro de version augmente tout seul : la nouvelle version s'installe **par-dessus** l'ancienne, sans perdre les favoris.

## Clé de signature — À GARDER PRÉCIEUSEMENT
Le fichier `app/louanges.keystore` est la « signature » officielle de l'application. Son mot de passe est écrit dans `app/build.gradle`.
- **Gardez une copie** de ce dossier en lieu sûr (clé USB, Google Drive). Sans cette clé, les futures mises à jour ne pourront plus s'installer par-dessus l'ancienne version.
- Laissez le dépôt **privé** : ainsi personne d'autre ne peut fabriquer une fausse mise à jour en votre nom. Pour partager l'application, partagez simplement le fichier `.apk`.

## Contenu technique
- Application Android native (Java) qui affiche `assets/index.html` dans une WebView, sans internet.
- Android 7.0 et plus récent.
- Polices intégrées (Cormorant Garamond, Literata, Figtree — licence libre SIL OFL).
