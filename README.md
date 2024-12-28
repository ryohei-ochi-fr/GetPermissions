# GetPermissions

このプロジェクトは、Android アプリケーションで位置情報の権限を要求する方法を学ぶためのサンプルアプリです。


## ビルド手順

1.  Android Studio を開きます。
2.  「File」>「Open」から、このプロジェクトのルートディレクトリを選択します。
3.  「Build」>「Make Project」を選択して、プロジェクトをビルドします。

## 実行手順

1.  Android Studio のツールバーにある「Run」ボタン（緑色の再生ボタン）をクリックします。
2.  接続されているデバイスまたはエミュレーターを選択し、アプリを実行します。

## 画面遷移図(Mermaid記法)

```mermaid
graph TD

A[MainActivity] --> B(SecondScreen);
B -- Permission denied --> C(ThirdScreen);
B -- Permission granted --> D(FourthScreen);
C --> B(SecondScreen);

B --> E(FifthScreen);
F -- Button click --> D;
E -- Permission granted --> F(SixthScreen);
A -- App finished --> X[App closed]
A -- Permission denied --> E;

Y[App start] -- Permission granted --> D
Y -- Permission denied --> E

style A fill:#ccf,stroke:#333,stroke-width:2px
style B fill:#cff,stroke:#333,stroke-width:2px
style C fill:#cfc,stroke:#333,stroke-width:2px
style D fill:#ffc,stroke:#333,stroke-width:2px
style E fill:#fcf,stroke:#333,stroke-width:2px
style F fill:#fcc,stroke:#333,stroke-width:2px
```

```

## ADB コマンド

### 権限の剥奪
```ps
adb shell pm revoke com.example.getpermissions android.permission.ACCESS_FINE_LOCATION
adb shell pm revoke com.example.getpermissions android.permission.ACCESS_COARSE_LOCATION
```

### アプリのアンインストール

```ps
adb uninstall com.example.getpermissions
```

## 依存関係

このプロジェクトは、以下のライブラリを利用しています。

*   Jetpack Compose
*   Navigation Compose

## その他

質問や要望があれば、ご連絡ください。



