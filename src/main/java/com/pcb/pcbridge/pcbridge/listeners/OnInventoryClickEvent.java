/*
 * The MIT License
 *
 * Copyright 2016 Andy Saw
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.pcb.pcbridge.pcbridge.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.pcb.pcbridge.utils.listeners.AbstractListener;

public final class OnInventoryClickEvent extends AbstractListener
{
	@EventHandler(priority = EventPriority.NORMAL)
	public void OnClick(InventoryClickEvent event)
	{
		//event.getClickedInventory().getName();
		
		//SerialiseSendChest(event);
	}
	
	/**
	 * If the player just closed a SendChest, serialise the chest contents
	 * back to the chest owner's local player file
	 * 
	 * @param event
	 */
	private void SerialiseSendChest(InventoryClickEvent event)
	{
		/*File playerFile = new File(GetEnv().GetPlayerFolder(), uuid + ".yml");		
		YamlConfiguration reader = YamlConfiguration.loadConfiguration(playerFile);
		reader.getString("chest.contents", null);*/
	}
}