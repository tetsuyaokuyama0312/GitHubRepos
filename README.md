# GitHubリポジトリ検索アプリ

## 概要

GitHub のリポジトリ検索 API を利用し、キーワード検索・無限スクロールによる結果表示を行います。

実装にあたっては **Jetpack Compose / Hilt / Retrofit / Paging3** など、現在の Android
開発で一般的な技術スタックを用い、
可読性・拡張性を意識した構成としています。

---

## 機能

* GitHub リポジトリの検索
* 検索キーワードの debounce 処理
* 無限スクロール（Paging）による検索結果表示
* 検索結果タップ時に外部ブラウザでリポジトリページを表示
* ローディング / エラー表示

---

## アーキテクチャ

* MVVM アーキテクチャ
* ViewModel による状態管理
* Repository 層での API アクセス抽象化
* Paging3 によるページネーション

```
UI (Compose)
  ↓
ViewModel
  ↓
Repository
  ↓
GitHub API
```

---

## 使用技術・ライブラリ

### 言語・ビルド

* Kotlin
* Android Gradle Plugin

### UI

* Jetpack Compose
* Material3
* Navigation Compose

### 非同期・状態管理

* Kotlin Coroutines / Flow
* Paging3

### ネットワーク

* Retrofit
* OkHttp
* Kotlinx Serialization

### DI

* Hilt

### テスト

* JUnit
* Espresso

---

## バージョン情報（一部抜粋）

* Kotlin: 2.3.0
* Compose BOM: 2025.12.01
* Paging: 3.3.6
* Retrofit: 3.0.0
* OkHttp: 5.3.2
* Hilt: 2.57.2

※ 詳細は `libs.versions.toml` を参照してください。

---

## セットアップ

### GitHub API トークン

GitHub Search API を利用するため、Personal Access Token（PAT）を使用しています。

以下のように `local.properties` へ設定してください。

```
API_BASE_URL=https://api.github.com/
GITHUB_TOKEN=your_github_personal_access_token
```

※ トークンはリポジトリに含めていません。

---

## ビルド・実行方法

1. Android Studio で本リポジトリを開く
2. `local.properties` に API_BASE_URL、GITHUB_TOKENを追加
3. `app` モジュールを Run

Debug ビルドを前提としています。

---

## 補足

* リポジトリ詳細画面はアプリ内で実装せず、外部ブラウザ遷移としています

## 工夫した点・こだわった点

### 検索体験の向上

* 検索クエリ入力に **debounce 処理** を入れ、入力途中での不要な API リクエストを抑制しています
* 初期状態（未検索時）・ローディング・エラー・結果表示を明確に分け、状態に応じた UI 表示を行っています

### ページネーション設計

* GitHub Search API の特性を考慮し、**Paging3** を用いた無限スクロールを実装しています
* LoadState を用いて、初回ロードと追加ロード（append）で表示を分けています

---

## 技術選定の理由

### Jetpack Compose

* 宣言的 UI により、状態（UiState / LoadState）と UI の対応関係を明確にできるため
* 今後の Android 開発の主流であり、保守性・拡張性の観点で適しているため

### MVVM + Repository

* UI / 状態管理 / データ取得の責務を分離し、可読性とテスタビリティを高めるため
* ViewModel を中心に、検索クエリや Paging の状態を一元管理できるため

### Paging3

* ページネーション処理（ロード状態管理、リトライ、キャッシュ）を安全かつ簡潔に実装できるため
* GitHub Search API との相性が良く、実運用を想定した構成にできるため

### Retrofit + OkHttp

* Android でのデファクトスタンダードであり、実務での再現性が高いため
* Interceptor により認証やログ出力を責務分離できるため

### Kotlin Coroutines / Flow

* 非同期処理と状態のストリームをシンプルに表現できるため
* 検索クエリ変更 → データ再取得 の流れを宣言的に記述できるため

---

## 今回スコープ外とした点・今後の改善案

* リポジトリ詳細画面のアプリ内実装（今回は外部ブラウザ遷移で対応）
* お気に入り（ブックマーク）機能の実装
* ユニットテスト・UI テストの追加

上記については、時間的制約を考慮しスコープ外としましたが、
実運用を想定する場合は優先的に対応したいと考えています。

---

## AI利用について

以下の箇所・目的でAIツールを利用しました。

* ページネーションの実装方法調査
* READMEのテンプレート作成

---

## 動作デモ

以下は実装したアプリの動作デモです。

![demo](docs/demo.gif)
