package log.charter.song;

import java.util.List;

import log.charter.io.rs.xml.song.ArrangementAnchor;
import log.charter.io.rs.xml.song.ArrangementHandShape;
import log.charter.io.rs.xml.song.ArrangementLevel;
import log.charter.io.rs.xml.song.Chord;
import log.charter.util.CollectionUtils.ArrayList2;
import log.charter.util.CollectionUtils.HashMap2;
import log.charter.util.CollectionUtils.Pair;

public class Level {
	public static HashMap2<Integer, Level> fromArrangementLevels(final List<ArrangementLevel> arrangementLevels) {
		return new ArrayList2<>(arrangementLevels)
				.toMap(arrangementLevel -> new Pair<>(arrangementLevel.difficulty, new Level(arrangementLevel)));
	}

	public ArrayList2<ArrangementAnchor> anchors = new ArrayList2<>();
	public ArrayList2<ArrangementHandShape> handShapes = new ArrayList2<>();
	public ArrayList2<Chord> chords = new ArrayList2<>();
	public ArrayList2<Note> notes = new ArrayList2<>();

	public Level() {
	}

	private Level(final ArrangementLevel level) {
		anchors = new ArrayList2<>(level.anchors.list);
		handShapes = new ArrayList2<>(level.handShapes.list);
		chords = level.chords.list.map(Chord::new);
		notes = level.notes.list.map(Note::new);
	}
}
