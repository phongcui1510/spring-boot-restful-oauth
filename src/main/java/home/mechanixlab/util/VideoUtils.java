package home.mechanixlab.util;

import java.io.File;

import org.jcodec.api.awt.FrameGrab;
import org.jcodec.common.FileChannelWrapper;
import org.jcodec.common.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;


public class VideoUtils {

	
	public static int getThumbnail (File video, String destLocation) {
		try {
			int frameNumber = 150;
	        FileChannelWrapper in = null;
	        in = NIOUtils.readableFileChannel(video);
	        Picture frame = FrameGrab.getNativeFrame(in, frameNumber);
	        AWTUtil.savePicture(frame, "png", new File(destLocation));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
        return 1;
	}
}
