package io.github.virtualvulpes.createcuisine.block.inventory;

import com.simibubi.create.foundation.item.SmartInventory;

import io.github.virtualvulpes.createcuisine.block.entity.FermentationVatBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

public class FermentationVatInventory extends SmartInventory {
	private FermentationVatBlockEntity blockEntity;

	public FermentationVatInventory(int slots, FermentationVatBlockEntity be) {
		super(slots, be, 16, true);
		this.blockEntity = be;
		this.whenContentsChanged(be::notifyChangeOfContents);
	}

	@Override
	public SmartInventory whenContentsChanged(Runnable updateCallback) {
		return super.whenContentsChanged(() -> {
			updateCallback.run();
			blockEntity.notifyChangeOfContents();
		});
	}

	@Override
	public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
		StoragePreconditions.notBlankNotNegative(resource, maxAmount);
		if (!insertionAllowed)
			return 0;
		// Only insert if no other slot already has a stack of this item
		try (Transaction test = transaction.openNested()) {
			long contained = this.extract(resource, Long.MAX_VALUE, test);
			if (contained != 0) {
				// already have this item. can we stack?
				long maxStackSize = Math.min(stackSize, resource.getItem().getMaxStackSize());
				long space = Math.max(0, maxStackSize - contained);
				if (space <= 0) {
					// nope.
					return 0;
				} else {
					// yes!
					maxAmount = Math.min(space, maxAmount);
				}
			}
		}
		return super.insert(resource, maxAmount, transaction);
	}

	@Override
	protected void onContentsChanged(int slot) {
		blockEntity.notifyChangeOfContents();
	}
}
