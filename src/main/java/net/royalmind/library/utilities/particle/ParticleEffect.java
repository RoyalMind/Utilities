package net.royalmind.library.utilities.particle;

import org.bukkit.entity.*;
import org.bukkit.util.*;
import java.util.*;
import java.lang.reflect.*;
import org.bukkit.*;
import org.bukkit.util.Vector;

public enum ParticleEffect
{
    EXPLOSION_NORMAL("explode", 0, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    EXPLOSION_LARGE("largeexplode", 1, -1, new ParticleProperty[0]),
    EXPLOSION_HUGE("hugeexplosion", 2, -1, new ParticleProperty[0]),
    FIREWORKS_SPARK("fireworksSpark", 3, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    WATER_BUBBLE("bubble", 4, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER }),
    WATER_SPLASH("splash", 5, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    WATER_WAKE("wake", 6, 7, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    SUSPENDED("suspended", 7, -1, new ParticleProperty[] { ParticleProperty.REQUIRES_WATER }),
    SUSPENDED_DEPTH("depthSuspend", 8, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    CRIT("crit", 9, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    CRIT_MAGIC("magicCrit", 10, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    SMOKE_NORMAL("smoke", 11, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    SMOKE_LARGE("largesmoke", 12, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    SPELL("spell", 13, -1, new ParticleProperty[0]),
    SPELL_INSTANT("instantSpell", 14, -1, new ParticleProperty[0]),
    SPELL_MOB("mobSpell", 15, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),
    SPELL_MOB_AMBIENT("mobSpellAmbient", 16, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),
    SPELL_WITCH("witchMagic", 17, -1, new ParticleProperty[0]),
    DRIP_WATER("dripWater", 18, -1, new ParticleProperty[0]),
    DRIP_LAVA("dripLava", 19, -1, new ParticleProperty[0]),
    VILLAGER_ANGRY("angryVillager", 20, -1, new ParticleProperty[0]),
    VILLAGER_HAPPY("happyVillager", 21, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    TOWN_AURA("townaura", 22, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    NOTE("note", 23, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),
    PORTAL("portal", 24, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    ENCHANTMENT_TABLE("enchantmenttable", 25, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    FLAME("flame", 26, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    LAVA("lava", 27, -1, new ParticleProperty[0]),
    FOOTSTEP("footstep", 28, -1, new ParticleProperty[0]),
    CLOUD("cloud", 29, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    REDSTONE("reddust", 30, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),
    SNOWBALL("snowballpoof", 31, -1, new ParticleProperty[0]),
    SNOW_SHOVEL("snowshovel", 32, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),
    SLIME("slime", 33, -1, new ParticleProperty[0]),
    HEART("heart", 34, -1, new ParticleProperty[0]),
    BARRIER("barrier", 35, 8, new ParticleProperty[0]),
    ITEM_CRACK("iconcrack", 36, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA }),
    BLOCK_CRACK("blockcrack", 37, -1, new ParticleProperty[] { ParticleProperty.REQUIRES_DATA }),
    BLOCK_DUST("blockdust", 38, 7, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA }),
    WATER_DROP("droplet", 39, 8, new ParticleProperty[0]),
    ITEM_TAKE("take", 40, 8, new ParticleProperty[0]),
    MOB_APPEARANCE("mobappearance", 41, 8, new ParticleProperty[0]),
    DRAGONBREATH("dragonbreath", 42, 9, new ParticleProperty[0]),
    ENDROD("endrod", 43, 9, new ParticleProperty[0]),
    DAMAGEINDICATOR("damageindicator", 44, 9, new ParticleProperty[0]),
    SWEEPATTACK("sweepattack", 45, 9, new ParticleProperty[0]),
    FALLINGDUST("fallingdust", 46, 10, new ParticleProperty[] { ParticleProperty.REQUIRES_DATA });

    private static final Map<String, ParticleEffect> NAME_MAP;
    private static final Map<Integer, ParticleEffect> ID_MAP;
    private final String name;
    private final int id;
    private final int requiredVersion;
    private final List<ParticleProperty> properties;

    private ParticleEffect(final String name, final int id, final int requiredVersion, final ParticleProperty[] array) {
        this.name = name;
        this.id = id;
        this.requiredVersion = requiredVersion;
        this.properties = Arrays.asList(array);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public int getRequiredVersion() {
        return this.requiredVersion;
    }

    public boolean hasProperty(final ParticleProperty particleProperty) {
        return this.properties.contains(particleProperty);
    }

    public boolean isSupported() {
        return this.requiredVersion == -1 || ParticlePacket.getVersion() >= this.requiredVersion;
    }

    public static ParticleEffect fromName(final String s) {
        for (final Map.Entry<String, ParticleEffect> entry : ParticleEffect.NAME_MAP.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(s)) {
                continue;
            }
            return entry.getValue();
        }
        return null;
    }

    public static ParticleEffect fromId(final int n) {
        for (final Map.Entry<Integer, ParticleEffect> entry : ParticleEffect.ID_MAP.entrySet()) {
            if (entry.getKey() != n) {
                continue;
            }
            return entry.getValue();
        }
        return null;
    }

    private static boolean isWater(final Location location) {
        final Material type = location.getBlock().getType();
        return type == Material.WATER || type == Material.LEGACY_STATIONARY_WATER;
    }

    private static boolean isLongDistance(final Location location, final List<Player> list) {
        final String name = location.getWorld().getName();
        final Iterator<Player> iterator = list.iterator();
        while (iterator.hasNext()) {
            final Location location2 = iterator.next().getLocation();
            if (name.equals(location2.getWorld().getName())) {
                if (location2.distanceSquared(location) < 65536.0) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    private static boolean isDataCorrect(final ParticleEffect particleEffect, final ParticleData particleData) {
        return ((particleEffect == ParticleEffect.BLOCK_CRACK || particleEffect == ParticleEffect.BLOCK_DUST || particleEffect == ParticleEffect.FALLINGDUST) && particleData instanceof BlockData) || (particleEffect == ParticleEffect.ITEM_CRACK && particleData instanceof ItemData);
    }

    private static boolean isColorCorrect(final ParticleEffect particleEffect, final ParticleColor particleColor) {
        return ((particleEffect == ParticleEffect.SPELL_MOB || particleEffect == ParticleEffect.SPELL_MOB_AMBIENT || particleEffect == ParticleEffect.REDSTONE) && particleColor instanceof OrdinaryColor) || (particleEffect == ParticleEffect.NOTE && particleColor instanceof NoteColor);
    }

    public void display(final float n, final float n2, final float n3, final float n4, final int n5, final Location location, final double n6) {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(location)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, n, n2, n3, n4, n5, n6 > 256.0, null).sendTo(location, n6);
    }

    public void display(final float n, final float n2, final float n3, final float n4, final int n5, final Location location, final List<Player> list) {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(location)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, n, n2, n3, n4, n5, isLongDistance(location, list), null).sendTo(location, list);
    }

    public void display(final float n, final float n2, final float n3, final float n4, final int n5, final Location location, final Player... array) {
        this.display(n, n2, n3, n4, n5, location, Arrays.asList(array));
    }

    public void display(final Vector vector, final float n, final Location location, final double n2) {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (!this.hasProperty(ParticleProperty.DIRECTIONAL)) {
            throw new IllegalArgumentException("This particle effect is not directional");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(location)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, vector, n, n2 > 256.0, null).sendTo(location, n2);
    }

    public void display(final Vector vector, final float n, final Location location, final List<Player> list) {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (!this.hasProperty(ParticleProperty.DIRECTIONAL)) {
            throw new IllegalArgumentException("This particle effect is not directional");
        }
        if (this.hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(location)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, vector, n, isLongDistance(location, list), null).sendTo(location, list);
    }

    public void display(final Vector vector, final float n, final Location location, final Player... array) {
        this.display(vector, n, location, Arrays.asList(array));
    }

    public void display(final ParticleColor particleColor, final Location location, final double n) {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.COLORABLE)) {
            throw new ParticleColorException("This particle effect is not colorable");
        }
        if (!isColorCorrect(this, particleColor)) {
            throw new ParticleColorException("The particle color type is incorrect");
        }
        new ParticlePacket(this, particleColor, n > 256.0).sendTo(location, n);
    }

    public void display(final ParticleColor particleColor, final Location location, final List<Player> list) {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.COLORABLE)) {
            throw new ParticleColorException("This particle effect is not colorable");
        }
        if (!isColorCorrect(this, particleColor)) {
            throw new ParticleColorException("The particle color type is incorrect");
        }
        new ParticlePacket(this, particleColor, isLongDistance(location, list)).sendTo(location, list);
    }

    public void display(final ParticleColor particleColor, final Location location, final Player... array) {
        this.display(particleColor, location, Arrays.asList(array));
    }

    public void display(final ParticleData particleData, final float n, final float n2, final float n3, final float n4, final int n5, final Location location, final double n6) {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, particleData)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, n, n2, n3, n4, n5, n6 > 256.0, particleData).sendTo(location, n6);
    }

    public void display(final ParticleData particleData, final float n, final float n2, final float n3, final float n4, final int n5, final Location location, final List<Player> list) {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, particleData)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, n, n2, n3, n4, n5, isLongDistance(location, list), particleData).sendTo(location, list);
    }

    public void display(final ParticleData particleData, final float n, final float n2, final float n3, final float n4, final int n5, final Location location, final Player... array) {
        this.display(particleData, n, n2, n3, n4, n5, location, Arrays.asList(array));
    }

    public void display(final ParticleData particleData, final Vector vector, final float n, final Location location, final double n2) {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, particleData)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, vector, n, n2 > 256.0, particleData).sendTo(location, n2);
    }

    public void display(final ParticleData particleData, final Vector vector, final float n, final Location location, final List<Player> list) {
        if (!this.isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!this.hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, particleData)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, vector, n, isLongDistance(location, list), particleData).sendTo(location, list);
    }

    public void display(final ParticleData particleData, final Vector vector, final float n, final Location location, final Player... array) {
        this.display(particleData, vector, n, location, Arrays.asList(array));
    }

    static {
        NAME_MAP = new HashMap<String, ParticleEffect>();
        ID_MAP = new HashMap<Integer, ParticleEffect>();
        for (final ParticleEffect particleEffect : values()) {
            ParticleEffect.NAME_MAP.put(particleEffect.name, particleEffect);
            ParticleEffect.ID_MAP.put(particleEffect.id, particleEffect);
        }
    }

    public enum ParticleProperty
    {
        REQUIRES_WATER,
        REQUIRES_DATA,
        DIRECTIONAL,
        COLORABLE;
    }

    public abstract static class ParticleData
    {
        private final Material material;
        private final byte data;
        private final int[] packetData;

        public ParticleData(final Material material, final byte data) {
            this.material = material;
            this.data = data;
            this.packetData = new int[] { material.getId(), data };
        }

        public Material getMaterial() {
            return this.material;
        }

        public byte getData() {
            return this.data;
        }

        public int[] getPacketData() {
            return this.packetData;
        }

        public String getPacketDataString() {
            return "_" + this.packetData[0] + "_" + this.packetData[1];
        }
    }

    public static final class ItemData extends ParticleData
    {
        public ItemData(final Material material, final byte b) {
            super(material, b);
        }
    }

    public static final class BlockData extends ParticleData
    {
        public BlockData(final Material material, final byte b) {
            super(material, b);
            if (!material.isBlock()) {
                throw new IllegalArgumentException("The material is not a block");
            }
        }
    }

    public abstract static class ParticleColor
    {
        public abstract float getValueX();

        public abstract float getValueY();

        public abstract float getValueZ();
    }

    public static final class OrdinaryColor extends ParticleColor
    {
        private final int red;
        private final int green;
        private final int blue;

        public OrdinaryColor(final int red, final int green, final int blue) {
            if (red < 0) {
                throw new IllegalArgumentException("The red value is lower than 0");
            }
            if (red > 255) {
                throw new IllegalArgumentException("The red value is higher than 255");
            }
            this.red = red;
            if (green < 0) {
                throw new IllegalArgumentException("The green value is lower than 0");
            }
            if (green > 255) {
                throw new IllegalArgumentException("The green value is higher than 255");
            }
            this.green = green;
            if (blue < 0) {
                throw new IllegalArgumentException("The blue value is lower than 0");
            }
            if (blue > 255) {
                throw new IllegalArgumentException("The blue value is higher than 255");
            }
            this.blue = blue;
        }

        public OrdinaryColor(final Color color) {
            this(color.getRed(), color.getGreen(), color.getBlue());
        }

        public int getRed() {
            return this.red;
        }

        public int getGreen() {
            return this.green;
        }

        public int getBlue() {
            return this.blue;
        }

        @Override
        public float getValueX() {
            return this.red / 255.0f;
        }

        @Override
        public float getValueY() {
            return this.green / 255.0f;
        }

        @Override
        public float getValueZ() {
            return this.blue / 255.0f;
        }
    }

    public static final class NoteColor extends ParticleColor
    {
        private final int note;

        public NoteColor(final int note) {
            if (note < 0) {
                throw new IllegalArgumentException("The note value is lower than 0");
            }
            if (note > 24) {
                throw new IllegalArgumentException("The note value is higher than 24");
            }
            this.note = note;
        }

        @Override
        public float getValueX() {
            return this.note / 24.0f;
        }

        @Override
        public float getValueY() {
            return 0.0f;
        }

        @Override
        public float getValueZ() {
            return 0.0f;
        }
    }

    private static final class ParticleDataException extends RuntimeException
    {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleDataException(final String s) {
            super(s);
        }
    }

    private static final class ParticleColorException extends RuntimeException
    {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleColorException(final String s) {
            super(s);
        }
    }

    private static final class ParticleVersionException extends RuntimeException
    {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleVersionException(final String s) {
            super(s);
        }
    }

    public static final class ParticlePacket
    {
        private static int version;
        private static Class<?> enumParticle;
        private static Constructor<?> packetConstructor;
        private static Method getHandle;
        private static Field playerConnection;
        private static Method sendPacket;
        private static boolean initialized;
        private final ParticleEffect effect;
        private float offsetX;
        private final float offsetY;
        private final float offsetZ;
        private final float speed;
        private final int amount;
        private final boolean longDistance;
        private final ParticleData data;
        private Object packet;

        public ParticlePacket(final ParticleEffect effect, final float offsetX, final float offsetY, final float offsetZ, final float speed, final int amount, final boolean longDistance, final ParticleData data) {
            initialize();
            if (speed < 0.0f) {
                throw new IllegalArgumentException("The speed is lower than 0");
            }
            if (amount < 0) {
                throw new IllegalArgumentException("The amount is lower than 0");
            }
            this.effect = effect;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
            this.speed = speed;
            this.amount = amount;
            this.longDistance = longDistance;
            this.data = data;
        }

        public ParticlePacket(final ParticleEffect particleEffect, final Vector vector, final float n, final boolean b, final ParticleData particleData) {
            this(particleEffect, (float)vector.getX(), (float)vector.getY(), (float)vector.getZ(), n, 0, b, particleData);
        }

        public ParticlePacket(final ParticleEffect particleEffect, final ParticleColor particleColor, final boolean b) {
            this(particleEffect, particleColor.getValueX(), particleColor.getValueY(), particleColor.getValueZ(), 1.0f, 0, b, null);
            if (particleEffect == ParticleEffect.REDSTONE && particleColor instanceof OrdinaryColor && ((OrdinaryColor)particleColor).getRed() == 0) {
                this.offsetX = Float.MIN_NORMAL;
            }
        }

        public static void initialize() {
            if (ParticlePacket.initialized) {
                return;
            }
            try {
                final String serverVersion = ReflectionUtils.PackageType.getServerVersion();
                ParticlePacket.version = Integer.parseInt(serverVersion.substring(serverVersion.indexOf("_") + 1, serverVersion.lastIndexOf("_")));
                if (ParticlePacket.version > 7) {
                    ParticlePacket.enumParticle = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
                }
                ParticlePacket.packetConstructor = ReflectionUtils.getConstructor(ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass((ParticlePacket.version < 7) ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles"), (Class<?>[])new Class[0]);
                ParticlePacket.getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle", (Class<?>[])new Class[0]);
                ParticlePacket.playerConnection = ReflectionUtils.getField("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, false, "playerConnection");
                ParticlePacket.sendPacket = ReflectionUtils.getMethod(ParticlePacket.playerConnection.getType(), "sendPacket", ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet"));
            }
            catch (Exception ex) {
                throw new VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", ex);
            }
            ParticlePacket.initialized = true;
        }

        public static int getVersion() {
            if (!ParticlePacket.initialized) {
                initialize();
            }
            return ParticlePacket.version;
        }

        public static boolean isInitialized() {
            return ParticlePacket.initialized;
        }

        private void initializePacket(final Location location) {
            if (this.packet != null) {
                return;
            }
            try {
                this.packet = ParticlePacket.packetConstructor.newInstance(new Object[0]);
                if (ParticlePacket.version < 8) {
                    String s = this.effect.getName();
                    if (this.data != null) {
                        s += this.data.getPacketDataString();
                    }
                    ReflectionUtils.setValue(this.packet, true, "a", s);
                }
                else {
                    ReflectionUtils.setValue(this.packet, true, "a", ParticlePacket.enumParticle.getEnumConstants()[this.effect.getId()]);
                    ReflectionUtils.setValue(this.packet, true, "j", this.longDistance);
                    if (this.data != null) {
                        final int[] packetData = this.data.getPacketData();
                        final Object packet = this.packet;
                        final boolean b = true;
                        final String s2 = "k";
                        final int[] array;
                        if (this.effect != ParticleEffect.ITEM_CRACK) { array = new int[] { packetData[0] | packetData[1] << 12 }; }
                        else { array = null; }
                        ReflectionUtils.setValue(packet, b, s2, array);
                    }
                }
                ReflectionUtils.setValue(this.packet, true, "b", (float)location.getX());
                ReflectionUtils.setValue(this.packet, true, "c", (float)location.getY());
                ReflectionUtils.setValue(this.packet, true, "d", (float)location.getZ());
                ReflectionUtils.setValue(this.packet, true, "e", this.offsetX);
                ReflectionUtils.setValue(this.packet, true, "f", this.offsetY);
                ReflectionUtils.setValue(this.packet, true, "g", this.offsetZ);
                ReflectionUtils.setValue(this.packet, true, "h", this.speed);
                ReflectionUtils.setValue(this.packet, true, "i", this.amount);
            }
            catch (Exception ex) {
                throw new PacketInstantiationException("Packet instantiation failed", ex);
            }
        }

        public void sendTo(final Location location, final Player player) {
            this.initializePacket(location);
            try {
                ParticlePacket.sendPacket.invoke(ParticlePacket.playerConnection.get(ParticlePacket.getHandle.invoke(player, new Object[0])), this.packet);
            }
            catch (Exception ex) {
                throw new PacketSendingException("Failed to send the packet to player '" + player.getName() + "'", ex);
            }
        }

        public void sendTo(final Location location, final List<Player> list) {
            if (list.isEmpty()) {
                throw new IllegalArgumentException("The player list is empty");
            }
            final Iterator<Player> iterator = list.iterator();
            while (iterator.hasNext()) {
                this.sendTo(location, iterator.next());
            }
        }

        public void sendTo(final Location location, final double n) {
            if (n < 1.0) {
                throw new IllegalArgumentException("The range is lower than 1");
            }
            final String name = location.getWorld().getName();
            final double n2 = n * n;
            for (final Player player : Bukkit.getOnlinePlayers()) {
                if (player.getWorld().getName().equals(name)) {
                    if (player.getLocation().distanceSquared(location) > n2) {
                        continue;
                    }
                    this.sendTo(location, player);
                }
            }
        }

        private static final class VersionIncompatibleException extends RuntimeException
        {
            private static final long serialVersionUID = 3203085387160737484L;

            public VersionIncompatibleException(final String s, final Throwable t) {
                super(s, t);
            }
        }

        private static final class PacketInstantiationException extends RuntimeException
        {
            private static final long serialVersionUID = 3203085387160737484L;

            public PacketInstantiationException(final String s, final Throwable t) {
                super(s, t);
            }
        }

        private static final class PacketSendingException extends RuntimeException
        {
            private static final long serialVersionUID = 3203085387160737484L;

            public PacketSendingException(final String s, final Throwable t) {
                super(s, t);
            }
        }
    }
}
