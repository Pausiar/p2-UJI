package es.uji.al449178.recsys;

public class LikedItemNotFoundException extends Exception {
    private final String itemName;

    public LikedItemNotFoundException(String itemName) {
        super("Item '" + itemName + "' no encontrado en el sistema de recomendacion");
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }
}
