package net.thedudemc.questformillions.common.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public class LootHandler {

	private JSONObject json;
	private JSONParser parser = new JSONParser();

	public static List<ItemStack> shulkers = new ArrayList<ItemStack>();
	public static HashMap<String, Loot> loot = new HashMap<String, Loot>();

	public LootHandler() {

	}

	public void init(File file) {
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				PrintWriter pw = new PrintWriter(file, "UTF-8");
				pw.print("{");
				pw.print("}");
				pw.flush();
				pw.close();
			}
			json = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			load();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void load() {
		if (!json.containsKey("loot")) {
			return;
		}
		// loot
		JSONObject jsonLoot = (JSONObject) json.get("loot");

		// Box
		for (Object o : jsonLoot.keySet()) {
			String boxName = (String) o;
			JSONObject box = (JSONObject) jsonLoot.get(boxName);
			JSONArray items = (JSONArray) box.get("items");
			List<ItemStack> boxItems = new ArrayList<ItemStack>();
			// item
			for (int i = 0; i < items.size(); i++) {
				JSONObject item = (JSONObject) items.get(i);
				String itemId = (String) item.get("id");
				int count = Math.toIntExact((long) item.get("count"));
				int meta = Math.toIntExact((long) item.get("metadata"));
				ItemStack stack = new ItemStack(Item.getByNameOrId(itemId), count, meta);
				if (item.containsKey("enchants")) {
					JSONArray enchantments = (JSONArray) item.get("enchants");
					for (int j = 0; j < enchantments.size(); j++) {
						JSONObject ench = (JSONObject) enchantments.get(j);
						String id = (String) ench.get("id");
						int lvl = Math.toIntExact((long) ench.get("lvl"));
						stack.addEnchantment(Enchantment.getEnchantmentByLocation(id), lvl);
					}
				}
				boxItems.add(stack);
			}

			NonNullList<ItemStack> list = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
			int k = 0;
			for (ItemStack stack : boxItems) {

				list.set(k++, stack);
			}

			loot.put(boxName, new Loot(list));

		}

	}

	private static ItemStack getPackedShulker(String boxName, NonNullList<ItemStack> list) {
		ItemStack shulker = new ItemStack(Item.getItemFromBlock(getRandomShulkerBox()));
		NBTTagCompound blockEntityTag = new NBTTagCompound();

		NBTTagCompound itemList = new NBTTagCompound();

		ItemStackHelper.saveAllItems(itemList, list);
		blockEntityTag.setTag("BlockEntityTag", itemList);
		shulker.setTagCompound(blockEntityTag);
		shulker.setStackDisplayName(boxName);

		return shulker;
	}

	public static ItemStack getRandomLoot() {

		if (loot.size() == 0)
			return ItemStack.EMPTY;

		Random rand = new Random();
		int index = rand.nextInt(loot.size());
		if (index == loot.size())
			index = 0;

		List<Map.Entry<String, Loot>> list = new ArrayList<Map.Entry<String, Loot>>(loot.entrySet());

		Collections.shuffle(list);
		for (Map.Entry<String, Loot> entry : list) {

			ItemStack stack = getPackedShulker(entry.getKey(), entry.getValue().items);
			return stack;
		}

		return ItemStack.EMPTY;
	}

	private static Block getRandomShulkerBox() {
		Random rand = new Random();
		int i = rand.nextInt(15);
		switch (i) {
		case 0:
			return Blocks.BLACK_SHULKER_BOX;
		case 1:
			return Blocks.BLUE_SHULKER_BOX;
		case 2:
			return Blocks.BROWN_SHULKER_BOX;
		case 3:
			return Blocks.CYAN_SHULKER_BOX;
		case 4:
			return Blocks.GRAY_SHULKER_BOX;
		case 5:
			return Blocks.GREEN_SHULKER_BOX;
		case 6:
			return Blocks.LIGHT_BLUE_SHULKER_BOX;
		case 7:
			return Blocks.LIME_SHULKER_BOX;
		case 8:
			return Blocks.MAGENTA_SHULKER_BOX;
		case 9:
			return Blocks.ORANGE_SHULKER_BOX;
		case 10:
			return Blocks.PINK_SHULKER_BOX;
		case 11:
			return Blocks.PURPLE_SHULKER_BOX;
		case 12:
			return Blocks.RED_SHULKER_BOX;
		case 13:
			return Blocks.SILVER_SHULKER_BOX;
		case 14:
			return Blocks.WHITE_SHULKER_BOX;
		case 15:
			return Blocks.YELLOW_SHULKER_BOX;
		default:
			return Blocks.PURPLE_SHULKER_BOX;
		}
	}

}
