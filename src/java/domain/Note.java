package domain;

import java.util.Date;

/**
 * The Class Note.
 */
public class Note {

	/** The note id. */
	private int noteId;
	
	/** The date created. */
	private Date dateCreated;
	
	/** The associated object id. */
	private int associatedObjectId;
	
	/**
	 * Instantiates a new note.
	 */
	public Note() {
		
	}

	/**
	 * Instantiates a new note.
	 *
	 * @param noteId the note id
	 * @param dateCreated the date created
	 * @param associatedObjectId the associated object id
	 */
	public Note(int noteId, Date dateCreated, int associatedObjectId) {
		this.noteId = noteId;
		this.dateCreated = dateCreated;
		this.associatedObjectId = associatedObjectId;
	}

	/**
	 * Gets the note id.
	 *
	 * @return the note id
	 */
	public int getNoteId() {
		return noteId;
	}

	/**
	 * Sets the note id.
	 *
	 * @param noteId the new note id
	 */
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	/**
	 * Gets the date created.
	 *
	 * @return the date created
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * Sets the date created.
	 *
	 * @param dateCreated the new date created
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * Gets the associated object id.
	 *
	 * @return the associated object id
	 */
	public int getAssociatedObjectId() {
		return associatedObjectId;
	}

	/**
	 * Sets the associated object id.
	 *
	 * @param associatedObjectId the new associated object id
	 */
	public void setAssociatedObjectId(int associatedObjectId) {
		this.associatedObjectId = associatedObjectId;
	}
	
	

}
