package xyz.unifycraft.unicore.api.utils.text

import gg.essential.elementa.components.UIText
import gg.essential.elementa.components.UIWrappedText
import gg.essential.universal.ChatColor
import xyz.unifycraft.unicore.api.UniCore
import java.awt.Color

/**
 * An all-around version independent text
 * handling class. Text made using this
 * class is automatically formatted and
 * translated both using Minecraft's
 * I18n and UniCore's localization
 * API. This class is also capable of
 * handling colour "tags". You can read
 * more about these tags at
 * @@WIKI_URL@@/text/color_tags
 */
interface Text : Iterable<Text> {
    /**
     * @return An exact replica of the text
     * this is called from.
     */
    fun copy(): Text

    /**
     * Sets the current contents of this
     * text to what is provided.
     *
     * @param text The text to set this text to.
     */
    fun set(text: String): Text
    /**
     * Sets the current contents of this
     * text to what is provided.
     *
     * @param text The text to set this text to.
     */
    fun set(text: Text): Text

    /**
     * Prepends the text provided to
     * the contents of this text.
     *
     * @param text The text to prepend.
     */
    fun prepend(text: String): Text
    /**
     * Prepends the text provided to
     * the contents of this text.
     *
     * @param text The text to prepend.
     */
    fun prepend(text: Text): Text

    /**
     * Appends the text provided to
     * the contents of this text.
     *
     * @param text The text to append.
     */
    fun append(text: String): Text
    /**
     * Appends the text provided to
     * the contents of this text.
     *
     * @param text The text to append.
     */
    fun append(text: Text): Text

    /**
     * Replaces the key provided with the value
     * provided in the contents of this text.
     *
     * @param key The key to replace.
     * @param value The value to replace the key with.
     * @param modify If true, the contents of this
     * text will be modified. If false, the contents
     * will be left as-is and you will be provided with
     * an instance of the text where your modifications
     * have been made.
     */
    fun replace(key: String, value: String, modify: Boolean = false): Text
    /**
     * Replaces the key provided with the value
     * provided in the contents of this text.
     *
     * @param key The key to replace.
     * @param value The value to replace the key with.
     * @param modify If true, the contents of this
     * text will be modified. If false, the contents
     * will be left as-is and you will be provided with
     * an instance of the text where your modifications
     * have been made.
     */
    fun replace(key: String, value: Text, modify: Boolean = false): Text
    /**
     * Replaces the key provided with the value
     * provided in the contents of this text.
     *
     * @param key The key to replace.
     * @param value The value to replace the key with.
     * @param modify If true, the contents of this
     * text will be modified. If false, the contents
     * will be left as-is and you will be provided with
     * an instance of the text where your modifications
     * have been made.
     */
    fun replace(key: Text, value: Text, modify: Boolean = false): Text
    /**
     * Replaces the key provided with the value
     * provided in the contents of this text.
     *
     * @param key The key to replace.
     * @param value The value to replace the key with.
     * @param modify If true, the contents of this
     * text will be modified. If false, the contents
     * will be left as-is and you will be provided with
     * an instance of the text where your modifications
     * have been made.
     */
    fun replace(key: Text, value: String, modify: Boolean = false): Text

    /**
     * @return The contents of this text, but
     * truncated to the length provided.
     */
    fun asTruncated(length: Int): Text

    /**
     * @return The contents of this text, raw.
     */
    fun asString(): String
    /**
     * @return The contents of this text but
     * formatted in a way such that Minecraft
     * can process it.
     */
    fun asFormattedString(): String
    /**
     * @return The contents of this text, but
     * truncated to the length provided.
     */
    fun asTruncatedString(length: Int): String
    /**
     * Automatically converts your text to an
     * Elementa component so it can be used
     * inside of GUIs.
     *
     * @return An Elementa text component using
     * your text's formatting and content.
     */
    fun asElementaText(shadow: Boolean = true, shadowColor: Color? = null): UIText
    /**
     * Automatically converts your text to an
     * Elementa component so it can be used
     * inside of GUIs.
     *
     * @return An Elementa text component using
     * your text's formatting and content.
     */
    fun asElementaWrappedText(
        shadow: Boolean = true,
        shadowColor: Color? = null,
        centered: Boolean = false,
        trimText: Boolean = false,
        lineSpacing: Float = 9f,
        trimmedTextSuffix: String = "..."
    ): UIWrappedText

    /**
     * Formats the text using the formatting
     * given.
     */
    fun format(vararg formatting: ChatColor): Text

    /**
     * Converts this text to a format of which Minecraft
     * can understand. In legacy versions, this is a Java
     * String, if modern versions this is vanilla Text.
     */
    fun toVanilla() = UniCore.getTextHelper().toVanilla(this)

    companion object {
        /**
         * @return A new instance of Text.
         */
        @JvmStatic
        fun create(text: String) = UniCore.getTextHelper().create(text)
    }
}
