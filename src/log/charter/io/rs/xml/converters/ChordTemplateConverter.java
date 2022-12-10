package log.charter.io.rs.xml.converters;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import log.charter.io.rs.xml.song.ChordTemplate;

public class ChordTemplateConverter implements Converter {
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(final Class type) {
		return type.equals(ChordTemplate.class);
	}

	@Override
	public void marshal(final Object source, final HierarchicalStreamWriter writer, final MarshallingContext context) {
		final ChordTemplate chordTemplate = (ChordTemplate) source;
		writer.addAttribute("chordName", chordTemplate.chordName);
		writer.addAttribute("displayName", chordTemplate.displayName);

		chordTemplate.fingers.forEach((string, finger) -> writer.addAttribute("finger" + string, "" + finger));
		chordTemplate.frets.forEach((string, fret) -> writer.addAttribute("fret" + string, "" + fret));
	}

	@Override
	public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
		final ChordTemplate chordTemplate = new ChordTemplate();
		for (int i = 0; i < reader.getAttributeCount(); i++) {
			final String name = reader.getAttributeName(i);
			final String value = reader.getAttribute(i);

			if (name.equals("chordName")) {
				chordTemplate.chordName = value;
			} else if (name.equals("displayName")) {
				chordTemplate.displayName = value;
			} else if (name.matches("finger[0-9]+")) {
				chordTemplate.fingers.put(Integer.valueOf(name.substring(6)), Integer.valueOf(value));
			} else if (name.matches("fret[0-9]+")) {
				chordTemplate.frets.put(Integer.valueOf(name.substring(4)), Integer.valueOf(value));
			}
		}

		return chordTemplate;
	}

}
