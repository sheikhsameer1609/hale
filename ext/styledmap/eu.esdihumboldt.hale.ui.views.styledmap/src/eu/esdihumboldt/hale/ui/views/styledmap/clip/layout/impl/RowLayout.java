/*
 * Copyright (c) 2012 Data Harmonisation Panel
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     HUMBOLDT EU Integrated Project #030962
 *     Data Harmonisation Panel <http://www.dhpanel.eu>
 */

package eu.esdihumboldt.hale.ui.views.styledmap.clip.layout.impl;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.mapviewer.JXMapViewer;

import eu.esdihumboldt.hale.ui.views.styledmap.clip.Clip;
import eu.esdihumboldt.hale.ui.views.styledmap.clip.impl.HorizontalClip;
import eu.esdihumboldt.hale.ui.views.styledmap.clip.layout.LayoutAugmentation;
import eu.esdihumboldt.hale.ui.views.styledmap.clip.layout.PainterLayout;
import eu.esdihumboldt.hale.ui.views.styledmap.clip.layout.extension.PainterProxy;

/**
 * Layout that organizes painters in horizontal rows.
 * 
 * @author Simon Templer
 */
public class RowLayout implements PainterLayout {

	/**
	 * Row layout augmentation
	 */
	public static class RowAugmentation extends AbstractDefaultAugmentation {

		private final int count;

		/**
		 * Create a row layout augmentation.
		 * 
		 * @param count the row count
		 */
		public RowAugmentation(int count) {
			this.count = count;
		}

		@Override
		public void doPaint(Graphics2D g, JXMapViewer map, List<PainterProxy> painters, int width,
				int height) {
			// between each pair of rows...
			for (int i = 1; i < count; i++) {
				int y = (int) (i * height / (float) count);

				// ..draw the name of the top painter
				if (i - 1 < painters.size()) {
					String name = painters.get(i - 1).getName();
					drawText(g, name, DEFAULT_MARGIN, y - DEFAULT_MARGIN);
				}

				// ...draw a line
				drawSplitLine(g, 0, y, width, y);

				// ..draw the name of the bottom painter
				if (i < painters.size()) {
					String name = painters.get(i).getName();
					drawText(g, name,
							width - DEFAULT_MARGIN - g.getFontMetrics().stringWidth(name), y
									+ DEFAULT_MARGIN + g.getFontMetrics().getAscent());
				}
			}
		}

	}

	/**
	 * @see PainterLayout#createClips(int)
	 */
	@Override
	public List<Clip> createClips(int count) {
		List<Clip> clips = new ArrayList<Clip>(count);
		float fCount = count;

		for (int i = 0; i < count; i++) {
			float start = i / fCount;
			float end = (i + 1) / fCount;

			clips.add(new HorizontalClip(start, end));
		}

		return clips;
	}

	/**
	 * @see PainterLayout#getAugmentation(int)
	 */
	@Override
	public LayoutAugmentation getAugmentation(int count) {
		return new RowAugmentation(count);
	}

}
