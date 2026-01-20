package dao;

public enum LendResult {
    SUCCESS,
    ALREADY,
    LIMIT,   // ← 追加（上限オーバー）
    FAIL
}