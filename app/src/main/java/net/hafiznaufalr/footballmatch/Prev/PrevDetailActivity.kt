package net.hafiznaufalr.footballmatch.Prev

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import net.hafiznaufalr.footballmatch.Model.Event
import net.hafiznaufalr.footballmatch.Model.Team
import net.hafiznaufalr.footballmatch.R
import net.hafiznaufalr.footballmatch.api.ApiReq
import net.hafiznaufalr.footballmatch.utils.invisible
import net.hafiznaufalr.footballmatch.utils.visible
import org.jetbrains.anko.*

class PrevDetailActivity : AppCompatActivity(),PrevInterface {
    private var resEventList: MutableList<Event> = mutableListOf()
    private lateinit var prevPresenter: PrevDetailPresenter

    private lateinit var resEvent: Event
    private lateinit var eventId: String

    private lateinit var strHomeName: String
    private lateinit var strAwayName: String
    private lateinit var homeTeamObj: Team
    private lateinit var awayTeamObj: Team
    private lateinit var homeBadgeUrl: String
    private lateinit var awayBadgeUrl: String

    private lateinit var progressBar: ProgressBar

    private lateinit var matchDate: TextView
    private lateinit var scoreText: TextView

    private lateinit var homeName: TextView
    private lateinit var homeBadges: ImageView
    private lateinit var homeFormation: TextView
    private lateinit var homeScorer: TextView
    private lateinit var homeGK: TextView
    private lateinit var homeDef: TextView
    private lateinit var homeMid: TextView
    private lateinit var homeForward: TextView
    private lateinit var homeSubs: TextView
    private lateinit var homeYellow: TextView
    private lateinit var homeRed: TextView

    private lateinit var awayName: TextView
    private lateinit var awayBadges: ImageView
    private lateinit var awayFormation: TextView
    private lateinit var awayScorer: TextView
    private lateinit var awayGK: TextView
    private lateinit var awayDef: TextView
    private lateinit var awayMid: TextView
    private lateinit var awayForward: TextView
    private lateinit var awaySubs: TextView
    private lateinit var awayYellow: TextView
    private lateinit var awayRed: TextView

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun setEvent(list: List<Event>) {
        resEvent = list[0]
        setView()
    }

    override fun setHomeBadges(teams: List<Team>) {
        homeTeamObj = teams[0]
        homeBadgeUrl = homeTeamObj.strTeamBadge
        Picasso.get().load(homeBadgeUrl).into(homeBadges)
    }
    override fun setAwayBadges(teams: List<Team>) {
        awayTeamObj = teams[0]
        awayBadgeUrl = awayTeamObj.strTeamBadge
        Picasso.get().load(awayBadgeUrl).into(awayBadges)
    }

    fun setView() {
        matchDate.text = resEvent.dateEvent
        scoreText.text = resEvent.intHomeScore + "-" + resEvent.intAwayScore

        homeName.text = resEvent.strHomeTeam
        homeFormation.text = resEvent.strHomeFormation
        homeScorer.text = resEvent.strHomeGoalDetails
        homeGK.text = resEvent.strHomeLineupGoalKeeper
        homeDef.text = resEvent.strHomeLineupDefense
        homeMid.text = resEvent.strHomeLineupMidfield
        homeForward.text = resEvent.strHomeLineupForward
        homeSubs.text = resEvent.strHomeLineupSubstitutes
        homeYellow.text = resEvent.strHomeYellowCards
        homeRed.text = resEvent.strHomeRedCards

        awayName.text = resEvent.strAwayTeam
        awayFormation.text = resEvent.strAwayFormation
        awayScorer.text = resEvent.strAwayGoalDetails
        awayGK.text = resEvent.strAwayLineupGoalkeeper
        awayDef.text = resEvent.strAwayLineupDefense
        awayMid.text = resEvent.strAwayLineupMidfield
        awayForward.text = resEvent.strAwayLineupForward
        awaySubs.text = resEvent.strAwayLineupSubstitutes
        awayYellow.text = resEvent.strAwayYellowCards
        awayRed.text = resEvent.strAwayRedCards
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventId = intent.getStringExtra("MATCH_ID")
        strHomeName = intent.getStringExtra("HOME_NAME")
        strAwayName = intent.getStringExtra("AWAY_NAME")

        // THE BIG LAYOUT
        relativeLayout {
            lparams(width = matchParent, height = wrapContent)
            // Scroll view for detail
            scrollView {
                relativeLayout{
                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                    linearLayout {
                        orientation = LinearLayout.VERTICAL


                        // Match Date
                        matchDate = textView {
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            height = wrapContent
                            width = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        // Home and Away team name
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // Home team
                            homeName = textView {
                                setTypeface(null, Typeface.BOLD)
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                            // Away team
                            awayName = textView {
                                setTypeface(null, Typeface.BOLD)
                                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }
                        // home away badge
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // Home badge
                            homeBadges = imageView {
                            }.lparams {
                                width = dip(80)
                                height = dip(80)
                                weight = 3f
                            }
                            // score
                            scoreText = textView {
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                                textSize = 28f
                                setTypeface(null, Typeface.BOLD)
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                weight = 1f
                                gravity = Gravity.CENTER_VERTICAL
                            }
                            // Away badge
                            awayBadges = imageView {
                            }.lparams {
                                width = dip(80)
                                height = dip(80)
                                weight = 3f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                        // Team formation
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // home formation
                            homeFormation = textView {
                                setTypeface(null, Typeface.BOLD)
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                            // away formation
                            awayFormation = textView {

                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }

                        // Goals text

                        textView {
                            text = "Goals"
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        // scorer name(s)
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // home scorer
                            homeScorer = textView {

                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 3f
                            }
                            // away scorer
                            awayScorer = textView {

                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 3f
                            }
                        }.lparams {
                            width = dip(0)
                            height = wrapContent
                        }

                        // GoalKeeper text
                        textView {
                            text = "Goal Keeper Lineup"
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        // Home and Away GK
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // Home team
                            homeGK = textView {

                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                            // Away team
                            awayGK = textView {
                                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }

                        // Defense Lineup header text
                        textView {
                            text = "Defense Lineup"
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        // Defense lineup
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // Home team
                            homeDef = textView {

                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                            // Away team
                            awayDef = textView {
                                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }

                        // Midfield lineup header text
                        textView {
                            text = "MidField Lineup"
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        // Midfield lineup
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // Home team
                            homeMid = textView {

                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                            // Away team
                            awayMid = textView {
                                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }

                        // Forward lineup header text
                        textView {
                            text = "Forward Lineup"
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        // Forward lineup
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // Home team
                            homeForward = textView {

                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                            // Away team
                            awayForward = textView {
                                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }

                        // Substitutes lineup header text
                        textView {
                            text = "Substitues Lineup"
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        // Substitutes lineup
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // Home team
                            homeSubs = textView {

                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                            // Away team
                            awaySubs = textView {
                                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }

                        // Yellow Cards header text
                        textView {
                            text = "Yellow Cards"
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        // Yellow Cards
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // Home team
                            homeYellow = textView {

                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                            // Away team
                            awayYellow = textView {
                                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }

                        textView {
                            text = "Red Cards"
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            homeRed = textView {

                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                            awayRed = textView {
                                textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                weight = 1f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                }.lparams(width = matchParent, height = wrapContent)
            }

        }
        init()
    }

    fun init() {
        // setup Presenter
        val request = ApiReq()
        val gson = Gson()
        prevPresenter = PrevDetailPresenter(this, request, gson)
        prevPresenter.getEventList(eventId)
        prevPresenter.getBadges("home",strHomeName)
        prevPresenter.getBadges("away",strAwayName)
    }
}
