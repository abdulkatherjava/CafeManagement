# CafeManagement

> Cafe Management

- have to have the sort process common for all modules such that menu items, order, order items
- search category when typing which means search with partial category string search

- 21-09-25
-
- save and get all works at the moment
- need to test all the APIs
- need to create the postman collection
- find the common classes for the pagination and sorting for all the services such that category, menu items

- 22-09-25
-
- All the end points from the Category controller working fine at the moment
- need to test all the APIs in other than Category controller
- need to create the postman collection done only for Category endpoints
- find the common classes for the pagination and sorting for all the services such that category, menu items
-

- 01-12-25

- category and menu items APIs are ready
- postman collection for both ready
- testing done for both
- had common sorting and pagination done

* 02-12-25

- refactored the package structure to have common, dto.category, dto.menuitem, service.category, service.menuitem
- updated the imports accordingly in all the files
- updated the PagedResponse to have List<SortRequest> instead of List<SortInfo>
- updated the PageableUtil to use the new PagedResponse and SortRequest imports
- updated the CategoryController and MenuItemController to use the new package structure and imports
- updated the CategoryService and MenuItemService to remove the old getAll methods and keep only the paged methods
- updated the PageableUtil to create List<SortRequest> from Page.getSort()
- tested all the APIs again to ensure everything works fine after refactoring

# - UPCOMING TASKS

- Handle the Barcode Duplicate Exception in MenuItemService when saving a new menu item with an existing barcode.
- Give the User Friendly Message like "Menu Item with barcode 'XYZ' already exists."
- tested the Barcode Duplicate Exception handling by trying to save a menu item with an existing barcode

- Handle the Exception When Duplicate Category Name is being created.
- Give the User Friendly Message like "Category with name 'ABC' already exists."
- tested the Duplicate Category Name Exception handling by trying to save a category with an existing name

- Next steps: create order and order item modules with similar structure and functionality
- create postman collection for order and order item modules
- test all the APIs for order and order item modules
-
