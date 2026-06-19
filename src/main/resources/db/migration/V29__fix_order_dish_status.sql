-- V29: Fix order_dish rows with invalid status DELIVERED (not a valid OrderDishStatus)
-- Valid values: WAITING, IN_PREPARATION, DONE
UPDATE order_dish SET status = 'DONE' WHERE status = 'DELIVERED';
