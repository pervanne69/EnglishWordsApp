package com.example.englishwordsapp


data class Word(
    val original: String,
    val translate: String,
    var learned: Boolean = false
)

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word
)

class LearnWordsTrainer {

    private val dictionary = listOf(
        Word("Vogon", "Вогон"),
        Word("Galaxy", "Галактика"),
        Word("Planet", "Планета"),
        Word("Starship", "Звездолет"),
        Word("Alien", "Инопланетянин"),
        Word("Astronaut", "Астронавт"),
        Word("Universe", "Вселенная"),
        Word("Comet", "Комета"),
        Word("Asteroid", "Астероид"),
        Word("Nebula", "Туманность"),
        Word("Black Hole", "Черная дыра"),
        Word("Supernova", "Сверхновая"),
        Word("Constellation", "Созвездие"),
        Word("Telescope", "Телескоп"),
        Word("Observatory", "Обсерватория"),
        Word("Satellite", "Спутник"),
        Word("Spacecraft", "Космический корабль"),
        Word("Rocket", "Ракета"),
        Word("Launchpad", "Стартовая площадка"),
        Word("Mission Control", "Центр управления полетами"),
        Word("Spacewalk", "Выход в открытый космос"),
        Word("Lunar Module", "Лунный модуль"),
        Word("Rover", "Марсоход"),
        Word("Space Station", "Космическая станция"),
        Word("Orbit", "Орбита"),
        Word("Gravity", "Гравитация"),
        Word("Light Year", "Световой год"),
        Word("Extraterrestrial", "Внеземной"),
        Word("Cosmic", "Космический"),
        Word("Celestial", "Небесный"),
        Word("Interstellar", "Межзвездный"),
        Word("Galaxy Cluster", "Скопление галактик"),
        Word("Dark Matter", "Темная материя"),
        Word("Dark Energy", "Темная энергия"),
        Word("Big Bang", "Большой взрыв"),
        Word("Spacetime", "Пространство-время"),
        Word("Wormhole", "Червоточина"),
        Word("Multiverse", "Мультивселенная"),
        Word("Quantum Physics", "Квантовая физика"),
        Word("Astrophysics", "Астрофизика"),
        Word("Cosmology", "Космология"),
        Word("Exoplanet", "Экзопланета"),
        Word("Habitable Zone", "Зона обитаемости"),
        Word("Search for Extraterrestrial Intelligence (SETI)", "Поиск внеземного разума (SETI)"),
        Word("Space Exploration", "Исследование космоса"),
        Word("Space Tourism", "Космический туризм"),
        Word("Space Colonization", "Колонизация космоса"),
        Word("Space Mining", "Космическая добыча"),
        Word("Space Debris", "Космический мусор"),
        Word("Space Weather", "Космическая погода"),
        Word("Solar Flare", "Солнечная вспышка"),
        Word("Geomagnetic Storm", "Геомагнитная буря"),
        Word("Aurora Borealis", "Северное сияние"),
        Word("Aurora Australis", "Южное сияние"),
        Word("Milky Way", "Млечный Путь"),
        Word("Andromeda Galaxy", "Галактика Андромеды"),
        Word("Large Magellanic Cloud", "Большое Магелланово Облако"),
        Word("Small Magellanic Cloud", "Малое Магелланово Облако"),
        Word("Orion Nebula", "Туманность Ориона"),
        Word("Crab Nebula", "Крабовидная туманность"),
        Word("Horsehead Nebula", "Туманность Конская Голова"),
        Word("Eagle Nebula", "Туманность Орёл"),
        Word("Pillars of Creation", "Столпы Творения"),
        Word("Great Red Spot", "Большое Красное Пятно"),
        Word("Olympus Mons", "Гора Олимп"),
        Word("Valles Marineris", "Долины Маринера"),
        Word("Hellas Planitia", "Равнина Эллада"),
        Word("Isidis Planitia", "Равнина Исиды"),
        Word("Elysium Planitia", "Равнина Элизий"),
        Word("Tharsis Montes", "Горы Фарсида"),
        Word("Olympus Mons", "Гора Олимп"),
        Word("Ascraeus Mons", "Гора Аскрийская"),
        Word("Pavonis Mons", "Гора Павлина"),
        Word("Arsia Mons", "Гора Арсия"),
        Word("International Space Station (ISS)", "Международная космическая станция (МКС)"),
        Word("Hubble Space Telescope", "Космический телескоп Хаббл"),
        Word("James Webb Space Telescope", "Космический телескоп Джеймса Уэбба"),
        Word("Chandra X-ray Observatory", "Рентгеновская обсерватория Чандра"),
        Word("Spitzer Space Telescope", "Космический телескоп Спитцер"),
        Word("Kepler Space Telescope", "Космический телескоп Кеплер"),
        Word("TESS", "TESS"),
        Word("Gaia", "Gaia")
    )

    private var currentQuestion: Question? = null

    fun getNextQuestion(): Question? {
        val notLearnedList = dictionary.filter { !it.learned }
        if (notLearnedList.isEmpty()) return null

        val questionWords =
            if (notLearnedList.size < NUMBER_OF_ANSWERS) {
                val learnedList = dictionary.filter { it.learned }.shuffled()
                notLearnedList.shuffled()
                    .take(NUMBER_OF_ANSWERS) + learnedList
                    .take(NUMBER_OF_ANSWERS - notLearnedList.size)
            } else {
                notLearnedList.shuffled().take(NUMBER_OF_ANSWERS)
            }.shuffled()
        val correctAnswer = questionWords.random()

        currentQuestion = Question(
            variants = questionWords,
            correctAnswer = correctAnswer
        )
        return currentQuestion
    }

    fun checkAnswer(userAnswerIndex: Int?): Boolean {
        return currentQuestion?.let {
            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.learned = true
                true
            } else {
                false
            }
        } ?: false
    }
}

const val NUMBER_OF_ANSWERS: Int = 4