package com.legacy.aether.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.legacy.aether.Aether;
import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.player.IPlayerAether;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemLifeShard extends Item
{

	public ItemLifeShard()
	{
		super();
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
	{
		IPlayerAether playerAether = AetherAPI.getInstance().get(player);
		ItemStack heldItem = player.getHeldItem(hand);

		if (worldIn.isRemote && playerAether.getShardsUsed() >= playerAether.getMaxShardCount())
		{
            Aether.proxy.sendMessage(player, "You can only use a total of 10 life shards.");
		}

		if (!worldIn.isRemote && playerAether.getShardsUsed() < playerAether.getMaxShardCount())
		{
			playerAether.updateShardCount(1);
			heldItem.shrink(1);

			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, heldItem);
	}

}