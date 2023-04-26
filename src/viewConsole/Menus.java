package viewConsole;

import java.util.*;

public class Menus {

	private static final MenuOption menuOptionExit = new MenuOption("Quitter", "", new MenuAction() {
		@Override
		public void doAction() {
			return;
			// System.exit(0);
		}
	});

	public static class Menu {
		private final String header;
		private final List<MenuOption> options = new ArrayList<>();
		private final Menu parentMenu;
		private final Set<String> ids = new HashSet<>();
		private final MenuOption menuOptionBack = new MenuOption("Retourner au menu supérieur", "", new MenuAction() {
			@Override
			public void doAction() {
				if (Menu.this.parentMenu != null) {
					Menu.this.parentMenu.show();
				}
			}
		});

		public Menu(Menu parent, String header) {
			this.parentMenu = parent;
			this.header = header;
		}

		public void addOptions(MenuOption... options) {
			for (MenuOption option : options) {
				if (ids.add(option.name)) {
					if (option.name.length() > getMaxTitleLength())
						throw new IllegalArgumentException(
								"La longueur maximale du titre de l'option est " + getMaxTitleLength());
					this.options.add(option);
				}
			}
		}

		public MenuOption findOptionByName(String name) {
			for (MenuOption option : this.options) {
				if (option.name.equals(name)) {
					return option;
				}
			}
			return null;
		}

		public List<MenuOption> getAllOption() {
			return new ArrayList<>(this.options);
		}

		public void clearOptions() {
			this.options.clear();
			this.ids.clear();
		}

		public void show() {
			System.out.println(header);
			int i = 1;
			Map<Integer, MenuOption> optionMap = new HashMap<>();
			if (this.parentMenu != null) {
				System.out.println(buildOptionText(i, this.menuOptionBack));
				optionMap.put(i++, this.menuOptionBack);
			}
			this.options.sort(Collections.reverseOrder());
			for (MenuOption option : this.options) {
				if (!option.show)
					continue;
				System.out.println(buildOptionText(i, option));
				optionMap.put(i++, option);
			}
			System.out.println(buildOptionText(i, menuOptionExit));
			optionMap.put(i, menuOptionExit);
			try {
				System.out.print(getInputPrefix());
				Scanner scanner = new Scanner(System.in);
				if (scanner.hasNext()) {
					int index = scanner.nextInt();
					if (index >= 1 && index <= i) {
						optionMap.get(index).doAction();
						if (index != i) {
							show();
						}
					} else {
						System.out.println("Veuillez saisir un numéro entre 1 et " + i);
						show();
					}
				}
				scanner.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Veuillez saisir un numéro entre 1 et " + i);
				show();
			}
		}

		protected String buildOptionText(int i, MenuOption option) {
			return String.format("%d) %-24s  %s", i, option.name, option.summary);
		}

		protected String getInputPrefix() {
			return "Votre choix > ";
		}

		protected int getMaxTitleLength() {
			return 50;
		}
	}

	public static class MenuOption implements Comparable<MenuOption> {
		private final String name;
		private final String summary;
		private boolean show;
		private MenuAction action;
		private final int priority;

		public MenuOption(String title, String summary, MenuAction action, int priority, boolean show) {
			this.name = title;
			this.summary = summary;
			this.action = action;
			this.priority = priority;
			this.show = show;
		}

		public MenuOption(String title, String summary, MenuAction action, int priority) {
			this(title, summary, action, priority, true);
		}

		public MenuOption(String title, String summary, MenuAction action) {
			this(title, summary, action, 0);
		}

		public void doAction() {
			if (this.action != null) {
				this.action.doAction();
			}
		}

		public MenuOption setShow(boolean isShow) {
			this.show = isShow;
			return this;
		}

		public MenuOption setAction(MenuAction action) {
			this.action = action;
			return this;
		}

		@Override
		public int compareTo(MenuOption o) {
			return this.priority - o.priority;
		}
	}

	public static class MenuAction {
		public void doAction() {
			System.out.println("C'est une action par défaut");
		}
	}

}
