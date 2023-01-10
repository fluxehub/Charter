package log.charter.gui.menuHandlers;

import java.awt.Dimension;

import javax.swing.JMenuBar;

import log.charter.data.ChartData;
import log.charter.data.copySystem.CopyManager;
import log.charter.data.managers.ModeManager;
import log.charter.data.managers.selection.SelectionManager;
import log.charter.data.undoSystem.UndoSystem;
import log.charter.gui.ChartPanelColors.ColorLabel;
import log.charter.gui.CharterFrame;
import log.charter.gui.chartPanelDrawers.common.AudioDrawer;
import log.charter.gui.handlers.AudioHandler;
import log.charter.gui.handlers.KeyboardHandler;
import log.charter.gui.handlers.SongFileHandler;
import log.charter.util.CollectionUtils.ArrayList2;

public class CharterMenuBar extends JMenuBar {
	private static final long serialVersionUID = -5784270027920161709L;

	private final EditMenuHandler editMenuHandler = new EditMenuHandler();
	private final FileMenuHandler fileMenuHandler = new FileMenuHandler();
	private final GuitarMenuHandler guitarMenuHandler = new GuitarMenuHandler();
	private final InfoMenuHandler infoMenuHandler = new InfoMenuHandler();
	private final ArrangementMenuHandler arrangementMenuHandler = new ArrangementMenuHandler();
	private final NotesMenuHandler notesMenuHandler = new NotesMenuHandler();
	private final VocalsMenuHandler vocalsMenuHandler = new VocalsMenuHandler();

	private final ArrayList2<CharterMenuHandler> menus = new ArrayList2<>(//
			fileMenuHandler, //
			editMenuHandler, //
			arrangementMenuHandler, //
			vocalsMenuHandler, //
			notesMenuHandler, //
			guitarMenuHandler, //
			infoMenuHandler);

	public void init(final AudioDrawer audioDrawer, final AudioHandler audioHandler, final CopyManager copyManager,
			final ChartData data, final CharterFrame frame, final KeyboardHandler keyboardHandler,
			final ModeManager modeManager, final SelectionManager selectionManager,
			final SongFileHandler songFileHandler, final UndoSystem undoSystem) {
		editMenuHandler.init(copyManager, data, frame, selectionManager, undoSystem);
		fileMenuHandler.init(data, frame, songFileHandler);
		infoMenuHandler.init(frame, this);
		guitarMenuHandler.init(data, frame, keyboardHandler, modeManager, selectionManager, undoSystem);
		arrangementMenuHandler.init(audioDrawer, audioHandler, data, frame, this, modeManager, selectionManager);
		notesMenuHandler.init(frame, data, modeManager);
		vocalsMenuHandler.init(data, frame, modeManager, selectionManager, undoSystem);

		final Dimension size = new Dimension(100, 20);
		setMinimumSize(size);
		this.setSize(size);
		setMaximumSize(size);

		setBackground(ColorLabel.BASE_BG_2.color());

		refreshMenus();

		frame.setJMenuBar(this);
	}

	public void refreshMenus() {
		removeAll();

		menus.stream()//
				.filter(menu -> menu.isApplicable())//
				.forEach(menu -> this.add(menu.prepareMenu()));

		validate();
	}
}
