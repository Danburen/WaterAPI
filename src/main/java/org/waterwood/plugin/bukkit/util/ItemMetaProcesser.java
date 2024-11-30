package org.waterwood.plugin.bukkit.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemMetaProcesser {
    /**
     * 获取指定命名空间下所有的键值对
     *
     * @param itemStack 目标 ItemStack
     * @param namespace 要查找的命名空间
     * @return 一个 Map，包含命名空间下的所有键值对
     */
    public static Map<NamespacedKey, Object> getNamespaceKeys(ItemStack itemStack, String namespace) {
        Map<NamespacedKey, Object> result = new HashMap<>();

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return result;
        }

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        Set<NamespacedKey> keys = dataContainer.getKeys();

        for (NamespacedKey key : keys) {
            if (key.getNamespace().equals(namespace)) {
                if (dataContainer.has(key, PersistentDataType.STRING)) {
                    result.put(key, dataContainer.get(key, PersistentDataType.STRING));
                } else if (dataContainer.has(key, PersistentDataType.INTEGER)) {
                    result.put(key, dataContainer.get(key, PersistentDataType.INTEGER));
                } else if (dataContainer.has(key, PersistentDataType.LONG)) {
                    result.put(key, dataContainer.get(key, PersistentDataType.LONG));
                } else if (dataContainer.has(key, PersistentDataType.BYTE)) {
                    result.put(key, dataContainer.get(key, PersistentDataType.BYTE));
                } else if (dataContainer.has(key, PersistentDataType.DOUBLE)) {
                    result.put(key, dataContainer.get(key, PersistentDataType.DOUBLE));
                } else if (dataContainer.has(key, PersistentDataType.FLOAT)) {
                    result.put(key, dataContainer.get(key, PersistentDataType.FLOAT));
                } else if (dataContainer.has(key, PersistentDataType.BOOLEAN)) {
                    result.put(key, dataContainer.get(key, PersistentDataType.BOOLEAN));
                } else if (dataContainer.has(key, PersistentDataType.BYTE_ARRAY)) {
                    result.put(key, dataContainer.get(key, PersistentDataType.BYTE_ARRAY));
                } else if (dataContainer.has(key, PersistentDataType.INTEGER_ARRAY)) {
                    result.put(key, dataContainer.get(key, PersistentDataType.INTEGER_ARRAY));
                } else if (dataContainer.has(key, PersistentDataType.LONG_ARRAY)) {
                    result.put(key, dataContainer.get(key, PersistentDataType.LONG_ARRAY));
                }
            }
        }

        return result;
    }
    /**
     * 获取指定命名空间下所有指定类型的键值对
     *
     * @param <T>         键值对类型
     * @param itemStack   目标 ItemStack
     * @param namespace   要查找的命名空间
     * @param dataType    数据类型
     * @return 一个 Map，包含命名空间下所有指定类型的键值对
     */
    private  static <T> Map<NamespacedKey, T> getNamespaceKeys(ItemStack itemStack, String namespace, PersistentDataType<?, T> dataType) {
        Map<NamespacedKey, T> result = new HashMap<>();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return result;

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        Set<NamespacedKey> keys = dataContainer.getKeys();

        for (NamespacedKey key : keys) {
            if (key.getNamespace().equals(namespace) && dataContainer.has(key, dataType)) {
                result.put(key, dataContainer.get(key, dataType));
            }
        }
        return result;
    }

    /**
     * 获取指定命名空间下所有的 String 类型的键值对
     *
     * @param itemStack 目标 ItemStack
     * @param namespace 要查找的命名空间
     * @return 一个 Map，包含命名空间下所有 String 类型的键值对
     */
    public static Map<NamespacedKey, String> getNamespaceStringKeys(ItemStack itemStack, String namespace) {
        return getNamespaceKeys(itemStack, namespace, PersistentDataType.STRING);
    }

    /**
     * 获取指定命名空间下所有的 Integer 类型的键值对
     *
     * @param itemStack 目标 ItemStack
     * @param namespace 要查找的命名空间
     * @return 一个 Map，包含命名空间下所有 Integer 类型的键值对
     */
    public static Map<NamespacedKey, Integer> getNamespaceIntegerKeys(ItemStack itemStack, String namespace) {
        return getNamespaceKeys(itemStack, namespace, PersistentDataType.INTEGER);
    }

    /**
     * 获取指定命名空间下所有的 Long 类型的键值对
     *
     * @param itemStack 目标 ItemStack
     * @param namespace 要查找的命名空间
     * @return 一个 Map，包含命名空间下所有 Long 类型的键值对
     */
    public static Map<NamespacedKey, Long> getNamespaceLongKeys(ItemStack itemStack, String namespace) {
        return getNamespaceKeys(itemStack, namespace, PersistentDataType.LONG);
    }

    /**
     * 获取指定命名空间下所有的 Byte 类型的键值对
     *
     * @param itemStack 目标 ItemStack
     * @param namespace 要查找的命名空间
     * @return 一个 Map，包含命名空间下所有 Byte 类型的键值对
     */
    public static Map<NamespacedKey, Byte> getNamespaceByteKeys(ItemStack itemStack, String namespace) {
        return getNamespaceKeys(itemStack, namespace, PersistentDataType.BYTE);
    }

    /**
     * 获取指定命名空间下所有的 Double 类型的键值对
     *
     * @param itemStack 目标 ItemStack
     * @param namespace 要查找的命名空间
     * @return 一个 Map，包含命名空间下所有 Double 类型的键值对
     */
    public static Map<NamespacedKey, Double> getNamespaceDoubleKeys(ItemStack itemStack, String namespace) {
        return getNamespaceKeys(itemStack, namespace, PersistentDataType.DOUBLE);
    }

    /**
     * 获取指定命名空间下所有的 Float 类型的键值对
     *
     * @param itemStack 目标 ItemStack
     * @param namespace 要查找的命名空间
     * @return 一个 Map，包含命名空间下所有 Float 类型的键值对
     */
    public static Map<NamespacedKey, Float> getNamespaceFloatKeys(ItemStack itemStack, String namespace) {
        return getNamespaceKeys(itemStack, namespace, PersistentDataType.FLOAT);
    }

    /**
     * 获取指定命名空间下所有的 Boolean 类型的键值对
     *
     * @param itemStack 目标 ItemStack
     * @param namespace 要查找的命名空间
     * @return 一个 Map，包含命名空间下所有 Boolean 类型的键值对
     */
    public static Map<NamespacedKey, Boolean> getNamespaceBooleanKeys(ItemStack itemStack, String namespace) {
        return getNamespaceKeys(itemStack, namespace, PersistentDataType.BOOLEAN);
    }
}
