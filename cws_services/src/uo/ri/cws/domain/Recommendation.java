package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Recommendation extends BaseEntity {

	private boolean usedForVoucher = false;

	private Client sponsor;

	private Client recommended;

	Recommendation() {
	}

	public Recommendation(Client sponsor, Client rec) {
		ArgumentChecks.isNotNull(sponsor);
		ArgumentChecks.isNotNull(rec);
		Associations.Recommend.link(this, sponsor, rec);
	}

	public Client getRecommended() {
		return recommended;
	}

	public Client getSponsor() {
		return sponsor;
	}

	public void markAsUsed() {
		usedForVoucher = true;

	}

	public boolean isUsed() {
		return usedForVoucher;
	}

	void _setSponsor(Client sponsor) {
		this.sponsor = sponsor;
	}

	void _setRecommended(Client recommended) {
		this.recommended = recommended;
	}

}
