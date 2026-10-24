# Ticket Buster — Fire TV App

A minimal native Android wrapper around the Ticket Buster HTML5 game, built to
run on Amazon Fire TV via the system WebView component (not Chrome), which is
confirmed to fully support Xbox Bluetooth controllers on Fire TV.

- The entire game (`app/src/main/assets/game.html`) is bundled inside the app.
- No INTERNET permission is requested — the app never needs network access.
- High scores persist via the WebView's local storage, which is preserved
  across app restarts and device reboots.

## How this gets built

This repo includes a GitHub Actions workflow (`.github/workflows/build.yml`)
that automatically compiles a debug APK every time files are pushed to GitHub.
No local Android Studio install is required. See the setup instructions
provided alongside this project for the full walkthrough.

## Installing on Fire TV

1. Enable **Settings → My Fire TV → Developer Options → Apps from Unknown
   Sources**.
2. Install the **Downloader** app from the Fire TV Appstore.
3. Host the built `app-debug.apk` somewhere reachable by the Fire TV (a
   temporary file link is enough), then enter that URL in Downloader to
   fetch and install it.
4. Launch **Ticket Buster** from the Fire TV app grid.
5. Pair your Xbox controller via **Settings → Controllers & Bluetooth
   Devices → Add Bluetooth Devices**.

## Updating the game later

To update the game itself, replace the contents of
`app/src/main/assets/game.html` with the new version and push to GitHub —
the Actions workflow will rebuild the APK automatically.
