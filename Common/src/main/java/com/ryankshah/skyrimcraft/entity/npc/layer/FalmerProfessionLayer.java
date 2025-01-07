package com.ryankshah.skyrimcraft.entity.npc.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.Constants;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;

public class FalmerProfessionLayer<T extends LivingEntity & VillagerDataHolder, M extends EntityModel<T>> extends RenderLayer<T, M>
{
    private static final Int2ObjectMap<ResourceLocation> LEVEL_LOCATIONS = Util.make(new Int2ObjectOpenHashMap<>(), p_117657_ -> {
        p_117657_.put(1, ResourceLocation.tryParse("stone"));
        p_117657_.put(2, ResourceLocation.tryParse("iron"));
        p_117657_.put(3, ResourceLocation.tryParse("gold"));
        p_117657_.put(4, ResourceLocation.tryParse("emerald"));
        p_117657_.put(5, ResourceLocation.tryParse("diamond"));
    });
    private final ResourceManager resourceManager;
    private final String path;

    public FalmerProfessionLayer(RenderLayerParent<T, M> pRenderer, ResourceManager pResourceManager, String pPath) {
        super(pRenderer);
        this.resourceManager = pResourceManager;
        this.path = pPath;
    }

    public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
        if (!$$3.isInvisible()) {
            VillagerData $$10 = ((VillagerDataHolder)$$3).getVillagerData();
            VillagerType $$11 = $$10.getType();
            VillagerProfession $$12 = $$10.getProfession();
            M $$15 = this.getParentModel();
            ResourceLocation $$16 = this.getResourceLocation("type", BuiltInRegistries.VILLAGER_TYPE.getKey($$11));
            renderColoredCutoutModel($$15, $$16, $$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F);
            ((VillagerHeadModel)$$15).hatVisible(true);
            if ($$12 != VillagerProfession.NONE && !$$3.isBaby()) {
                ResourceLocation $$17 = this.getResourceLocation("profession", BuiltInRegistries.VILLAGER_PROFESSION.getKey($$12));
                renderColoredCutoutModel($$15, $$17, $$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F);
                if ($$12 != VillagerProfession.NITWIT) {
                    ResourceLocation $$18 = this.getResourceLocation("profession_level", (ResourceLocation)LEVEL_LOCATIONS.get(Mth.clamp($$10.getLevel(), 1, LEVEL_LOCATIONS.size())));
                    renderColoredCutoutModel($$15, $$18, $$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F);
                }
            }

        }
    }

    private ResourceLocation getResourceLocation(String pFolder, ResourceLocation pLocation) {
        return new ResourceLocation(Constants.MODID, "textures/entity/" + this.path + "/" + pFolder + "/" + pLocation.getPath() + ".png");
    }
}
