package nl.workingtalent.backend.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reservation {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		
		@Column(nullable = false)
		private Date reservationDate;
		
		@Column(nullable = false)
		private boolean processed;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Date getReservationDate() {
			return reservationDate;
		}

		public void setReservationDate(Date reservationDate) {
			this.reservationDate = reservationDate;
		}

		public boolean isProcessed() {
			return processed;
		}

		public void setProcessed(boolean processed) {
			this.processed = processed;
		}
		
		
}
