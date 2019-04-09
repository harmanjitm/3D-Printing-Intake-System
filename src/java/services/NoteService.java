package services;

import java.util.ArrayList;

import domain.Note;
import persistence.NoteBroker;

/**
 * The Class NoteService provides methods to access and modify archive objects.
 * 
 *  EXTRANEOUS CLASS. SHOULD PROBABLY DELETE
 */
public class NoteService {

	/** The note broker to persist changes to the database. */
	@SuppressWarnings("unused")
	private NoteBroker nb;
	
	/**
	 * Instantiates a new note service object.
	 */
	public NoteService() {
	}
	
	/**
	 * Creates a new note.
	 *
	 * @return the new note
	 */
	public Note createNote() {
		return null;
		
	}
	
	/**
	 * Delete note by note id.
	 *
	 * @param noteId the note id of the note to be deleted
	 * @return the note that was deleted or null if note not found
	 */
	public Note deleteNote(int noteId) {
		return null;
		
	}
	
	/**
	 * Update replaces exiting note with an updated instance.
	 *
	 * @param toUpdate the material to update
	 * @return the note that was replaced or null if update fails
	 */
	public Note updateNote(Note toUpdate) {
		return null;
		
	}
	
	/**
	 * Gets the all notes in an arrayList.
	 *
	 * @return the all notes
	 */
	public ArrayList<Note> getAllNotes() {
		return null;
		
	}
	
	/**
	 * Gets a note by id.
	 *
	 * @param noteId the note id of the note to be retrieved
	 * @return the note with that id or null if no note is found
	 */
	public Note getNote(int noteId) {
		return null;
		
	}

	/**
	 * Gets the next id.
	 *
	 * @return the next id
	 */
	public int getNextId() {
		return 0;
	}
}
