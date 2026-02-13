#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

/**
 * Tauri 应用程序入口点
 * 负责初始化并运行桌面应用程序
 */
fn main() {
    sdkhub_lib::run()
}
