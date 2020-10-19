package ninja.soroosh.chatopia.core.runner;

public sealed interface Option permits CallbackDataOption, CallbackURLOption {
    String getText();
}
