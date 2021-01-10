
import java.awt.Image;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.ImageIcon;

public class Images {

	private final String IMAGE_DIR = "files/";
	private final String whitePawnDir = "files/white_pawn.png";
	private final String blackPawnDir = "files/black_pawn.png";
	private final String whiteRookDir = "files/white_rook.png";
	private final String blackRookDir = "files/black_rook.png";
	private final String whiteKnightDir = "files/white_knight.png";
	private final String blackKnightDir = "files/black_knight.png";
	private final String whiteBishopDir = "files/white_bishop.png";
	private final String blackBishopDir = "files/black_bishop.png";
	private final String whiteQueenDir = "files/white_queen.png";
	private final String blackQueenDir = "files/black_queen.png";
	private final String whiteKingDir = "files/white_king.png";
	private final String blackKingDir = "files/black_king.png";
	private Map<String, ImageIcon> images;

	public Images() {
		images = new TreeMap<String, ImageIcon>();
		scaleImages();
	}

	private void scaleImages() {
		ImageIcon wP = null;
		ImageIcon wN = null;
		ImageIcon wB = null;
		ImageIcon wR = null;
		ImageIcon wQ = null;
		ImageIcon wK = null;
		ImageIcon bP = null;
		ImageIcon bN = null;
		ImageIcon bB = null;
		ImageIcon bR = null;
		ImageIcon bQ = null;
		ImageIcon bK = null;
		if (getClass().getResource(whitePawnDir) != null) {
			wP = new ImageIcon(getClass().getResource(whitePawnDir));
			Image pic = wP.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			wP = new ImageIcon(pic);
			images.put("wP", wP);
		}
		if (getClass().getResource(whiteKnightDir) != null) {
			wN = new ImageIcon(getClass().getResource(whiteKnightDir));
			Image pic = wN.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			wN = new ImageIcon(pic);
			images.put("wN", wN);
		}
		if (getClass().getResource(whiteBishopDir) != null) {
			wB = new ImageIcon(getClass().getResource(whiteBishopDir));
			Image pic = wB.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			wB = new ImageIcon(pic);
			images.put("wB", wB);
		}
		if (getClass().getResource(whiteRookDir) != null) {
			wR = new ImageIcon(getClass().getResource(whiteRookDir));
			Image pic = wR.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			wR = new ImageIcon(pic);
			images.put("wR", wR);
		}
		if (getClass().getResource(whiteQueenDir) != null) {
			wQ = new ImageIcon(getClass().getResource(whiteQueenDir));
			Image pic = wQ.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			wQ = new ImageIcon(pic);
			images.put("wQ", wQ);
		}
		if (getClass().getResource(whiteKingDir) != null) {
			wK = new ImageIcon(getClass().getResource(whiteKingDir));
			Image pic = wK.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			wK = new ImageIcon(pic);
			images.put("wK", wK);
		}
		if (getClass().getResource(blackPawnDir) != null) {
			bP = new ImageIcon(getClass().getResource(blackPawnDir));
			Image pic = bP.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			bP = new ImageIcon(pic);
			images.put("bP", bP);
		}
		if (getClass().getResource(blackKnightDir) != null) {
			bN = new ImageIcon(getClass().getResource(blackKnightDir));
			Image pic = bN.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			bN = new ImageIcon(pic);
			images.put("bN", bN);
		}
		if (getClass().getResource(blackBishopDir) != null) {
			bB = new ImageIcon(getClass().getResource(blackBishopDir));
			Image pic = bB.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			bB = new ImageIcon(pic);
			images.put("bB", bB);
		}
		if (getClass().getResource(blackRookDir) != null) {
			bR = new ImageIcon(getClass().getResource(blackRookDir));
			Image pic = bR.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			bR = new ImageIcon(pic);
			images.put("bR", bR);
		}
		if (getClass().getResource(blackQueenDir) != null) {
			bQ = new ImageIcon(getClass().getResource(blackQueenDir));
			Image pic = bQ.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			bQ = new ImageIcon(pic);
			images.put("bQ", bQ);
		}
		if (getClass().getResource(blackKingDir) != null) {
			bK = new ImageIcon(getClass().getResource(blackKingDir));
			Image pic = bK.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);
			bK = new ImageIcon(pic);
			images.put("bK", bK);
		}
	}

	public ImageIcon getImage(String image) {
		if (images.containsKey(image)) {
			return images.get(image);
		} else {
			return null;
		}
	}

}
