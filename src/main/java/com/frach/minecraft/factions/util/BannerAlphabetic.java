package com.frach.minecraft.factions.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public enum BannerAlphabetic {

    A(new Pattern[]{
            new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL),
            new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM),
            new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT),
            new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP),
            new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT),
            new Pattern(DyeColor.BLACK, PatternType.BORDER)
    }),
    B(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    C(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    D(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    E(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.BLACK, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    F(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.BLACK, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    G(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    H(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    I(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    J(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    K(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.BLACK, PatternType.HALF_VERTICAL_MIRROR), new Pattern(DyeColor.WHITE, PatternType.CROSS), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    L(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    M(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_TOP), new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    N(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.BLACK, PatternType.DIAGONAL_RIGHT_MIRROR), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    O(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    P(new Pattern[]{
            new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL),
            new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT),
            new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM),
            new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT),
            new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP),
            new Pattern(DyeColor.BLACK, PatternType.BORDER)}
    ),
    Q(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.SQUARE_BOTTOM_RIGHT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    R(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL_MIRROR), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    S(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT), new Pattern(DyeColor.BLACK, PatternType.BORDER), new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER)}),
    T(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    U(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    V(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    W(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.TRIANGLE_BOTTOM), new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    X(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.BLACK, PatternType.STRIPE_CENTER), new Pattern(DyeColor.WHITE, PatternType.CROSS), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    Y(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.CROSS), new Pattern(DyeColor.BLACK, PatternType.HALF_VERTICAL_MIRROR), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    Z(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    ONE(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.SQUARE_TOP_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    TWO(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    THREE(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.BLACK, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    FOUR(new Pattern[]{new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    FIVE(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT), new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.SQUARE_BOTTOM_LEFT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    SIX(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    SEVEN(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.BLACK, PatternType.DIAGONAL_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT), new Pattern(DyeColor.WHITE, PatternType.SQUARE_BOTTOM_LEFT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    EIGHT(new Pattern[]{
            new Pattern(DyeColor.BLACK, PatternType.STRIPE_CENTER),
            new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP),
            new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM),
            new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE),
            new Pattern(DyeColor.BLACK, PatternType.BORDER)}
    ),
    NINE(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL_MIRROR), new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE), new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.BLACK, PatternType.BORDER)}),
    ZERO(new Pattern[]{new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP), new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM), new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT), new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT), new Pattern(DyeColor.BLACK, PatternType.BORDER)});

    private Pattern[] patterns = null;

    private BannerAlphabetic(Pattern[] patterns) {
        this.patterns = patterns;
    }

    public Pattern[] getPatterns() {
        return patterns;
    }

    public ItemStack getBanner() {
        return getBanner(DyeColor.WHITE, DyeColor.BLACK);
    }

    public ItemStack getBanner(DyeColor letterColor, DyeColor backColor) {
        ItemStack banner = new ItemStack(Material.LEGACY_BANNER);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();
        bannerMeta.setBaseColor(backColor);

        for (Pattern patterns : getPatterns()) {
            bannerMeta.addPattern(patterns.getColor() == DyeColor.BLACK ? new Pattern(backColor, patterns.getPattern()) : new Pattern(letterColor, patterns.getPattern()));
        }
        banner.setItemMeta(bannerMeta);
        return banner;
    }

    public static BannerAlphabetic getBannerByLetter(String letter) {
        for (BannerAlphabetic banners : values()) {
            if (banners.name().equalsIgnoreCase(letter)) {
                return banners;
            }
        }
        return null;
    }
}