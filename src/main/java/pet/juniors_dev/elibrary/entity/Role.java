package pet.juniors_dev.elibrary.entity;

public enum Role {
    ROLE_ADMIN("Администратор"),
    ROLE_USER("Пользователь");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
