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

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.thedudemc.questformillions.common.json.JSONArray;
import net.thedudemc.questformillions.common.json.JSONObject;
import net.thedudemc.questformillions.common.json.JSONParser;

public class LootHandler {

	private JSONObject json;
	private JSONParser parser = new JSONParser();

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
		// loot object
		JSONObject jsonLoot = (JSONObject) json.get("loot");

		// Box object
		for (Object o : jsonLoot.keySet()) {

			String boxName = (String) o;
			JSONObject box = (JSONObject) jsonLoot.get(boxName);
			JSONArray items = (JSONArray) box.get("items");

			List<ItemStack> boxItems = new ArrayList<ItemStack>();

			// item loop
			for (int i = 0; i < items.size(); i++) {
				JSONObject item = (JSONObject) items.get(i);

				// get id, count, and meta
				String itemId = (String) item.get("id");
				int count = Math.toIntExact((long) item.get("count"));
				int meta = Math.toIntExact((long) item.get("metadata"));

				// create the stack
				ItemStack stack = new ItemStack(Item.getByNameOrId(itemId), count, meta);

				// if has enchants, enchant it
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

			// create a list of itemstacks the size of shulker inventory.
			NonNullList<ItemStack> list = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);

			// for every item found in the items list, add it to the nonnulllist.
			int k = 0;
			for (ItemStack stack : boxItems) {

				list.set(k++, stack);
			}

			// add the shulker inventory to the list of loot.
			loot.put(boxName, new Loot(list));

		}

	}

	private static ItemStack getPackedShulker(String boxName, NonNullList<ItemStack> list) {
		// create shulker item
		ItemStack shulker = new ItemStack(Item.getItemFromBlock(getRandomShulkerBox()));

		// create empty nbt data
		NBTTagCompound blockEntityTag = new NBTTagCompound();
		NBTTagCompound itemList = new NBTTagCompound();

		// save all items in the passed list to nbt
		ItemStackHelper.saveAllItems(itemList, list);

		// set items list to blockentitytag (shulker's item nbt data)
		blockEntityTag.setTag("BlockEntityTag", itemList);
		shulker.setTagCompound(blockEntityTag);

		// set shulker box name
		shulker.setStackDisplayName(boxName);

		return shulker;
	}

	public static ItemStack getRandomLoot() {

		if (loot.size() == 0)
			return ItemStack.EMPTY;

		// get a random inventory of loot by creating a list of the entries and
		// shuffling them, then pull the first one
		List<Map.Entry<String, Loot>> list = new ArrayList<Map.Entry<String, Loot>>(loot.entrySet());

		Collections.shuffle(list);
		String name = list.get(0).getKey();
		Loot items = list.get(0).getValue();

		return getPackedShulker(name, items.items);

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
