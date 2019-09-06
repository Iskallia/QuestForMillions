package net.thedudemc.questformillions.common.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LootHandler {

	private File file;
	private JSONObject json;
	private JSONParser parser = new JSONParser();

	public static List<ItemStack> items = new ArrayList<ItemStack>();

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
		JSONObject loot = (JSONObject) json.get("loot");
		for (Object o : loot.keySet()) {
			String item = (String) o;
			JSONObject info = (JSONObject) loot.get(item);
			JSONArray enchantments = (JSONArray) info.get("enchants");
			int count = (int) info.get("count");
			ItemStack stack = new ItemStack(Item.getByNameOrId(item), count);
			for (int i = 0; i < enchantments.size(); i++) {
				JSONObject ench = (JSONObject) enchantments.get(i);
				String id = (String) ench.get("id");
				int lvl = (int) ench.get("lvl");
				stack.addEnchantment(Enchantment.getEnchantmentByLocation(id), lvl);
			}
			items.add(stack);

		}

	}

}
