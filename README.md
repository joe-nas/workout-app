## Iron Delirium Backend

### Description
This is the backend for the Iron Delirium project - A workout tracker which lets users login, save, edit and delete 
workout data, The backend inmplements a REST API built in Java with Spring and MongoDB.

All requests to the API must be authenticated with a valid JWT token. The token is generated by the frontend and sent in the
Authorization header of each request. The token is generated by the frontend using jsonwebtoken library and is issued
once logged in with either Google or Github. The token is valid for 1 hour and is refreshed automatically by the frontend
when it expires.

The token is validated by the backend using a OncePerRequestFilter that intercepts all requests in the SecurityFilterChain 
and validates the token using a NimbusJwtDecoder. The filter is configured to set an Authentication using a JwtAuthenticationToken
 

The following endpoints are currently implemented:

-------------

**Endpoint:** `GET /api/users/{oauthId}`

Retrieves user information by oauthID, which is the sub of either google or github, more providers will come in the future.

**Parameters:**

- `oatuthId` (path parameter): The unique identifier of the user.

**Requests:**

```http
GET /api/users/123234

    {
  "_id": "6560b55ec0d9646d365c234c",
  "oauthId": "11540068942960655",
  "userRoles": [
    "USER"
  ],
  "username": "Arnold Schwarzenegger",
  "email": "arnold@schwarzenegger.com",
  "metric": "KG",
  "oauthDetails": {
    "oauthProvider": "google",
    "oauthId": "11540068942960655"
  }
 }
```

-------------

**Endpoint:** `GET /api/user/create`

Creates a user. Needs a slight rework with regard to the duplicate oauthId.

**Requests:**

```http
POST /api/user/create

{
  "username": "Arnold Schwarzenegger",
  "email": "arnold@schwarzenegger.com",
  "oauthId": "11540068942960655",
  "oauthDetails": {
    "oauthProvider": "google",
    "oauthId": "11540068942960655",
  }
}
```

**Responses:**

```http
    {
  "_id": "6560b55ec0d9646d365c234c",
  "oauthId": "11540068942960655",
  "userRoles": [
    "USER"
  ],
  "username": "Arnold Schwarzenegger",
  "email": "arnold@schwarzenegger.com",
  "metric": "KG",
  "oauthDetails": {
    "oauthProvider": "google",
    "oauthId": "11540068942960655"
  }
 }
```

-------------

**Endpoint:** `GET /api/user/check/{oauthId}`

Checks if a user exists by oauthId.

**Parameters:**

- `oatuthId` (path parameter): The unique identifier of the user.

**Response:**

```http
Returns 200 if user exists, 404 if not.
```

-------------

**Endpoint:** `PUT /user/{oauthId}/profile`

Lets users update their user profile, allowed fields will be image, username, email address. On successful request, 
returns the updated user.

**Responses:**

```http
    {
  "_id": "6560b55ec0d9646d365c234c",
  "oauthId": "11540068942960655",
  "userRoles": [
    "USER"
  ],
  "username": "The Arnold Schwarzenegger",
  "email": "thearnold@schwarzenegger.com",
  "metric": "LBS",
  "oauthDetails": {
    "oauthProvider": "google",
    "oauthId": "11540068942960655"
  }
 }
```

-------------

**Endpoint:** `GET /api/user/{oauthId}/workouts`

Retrieves all workouts for a user by oauthID.

**Parameters:**

- `oatuthId` (path parameter): The unique identifier of the user.

**Requests:**

```http
GET /api/users/123234345/workouts

[
    {
        "_id": "653bff461dabdsdf365c1d614d",
        "user": {
            "$ref": "users",
            "$id": "653a844a532409de9064fbb138a"
        },
        "oauthId": "1154006894960655",
        "workoutName": "Monday Morning Workout",
        "dateCreated": {
            "date": "2023-10-27T17:35:53.212Z"
        },
        "exercises": [
            {
                "exerciseName": "Bench Press",
                "sets": [
                    {
                        "setNumber": 1,
                        "reps": 8,
                        "weight": 70,
                        "rpe": 8,
                        "isDone": true
                    },
                    {
                        "setNumber": 2,
                        "reps": 8,
                        "weight": 70,
                        "rpe": 9,
                        "isDone": true
                    }
                ]
            },
            {
                "exerciseName": "RDL",
                "sets": [
                    {
                        "setNumber": 1,
                        "reps": 15,
                        "weight": 90,
                        "rpe": 6,
                        "isDone": false
                    },
                    {
                        "setNumber": 2,
                        "reps": 15,
                        "weight": 90,
                        "rpe": 7,
                        "isDone": false
                    }
                ]
            }
        ]
    }
]
```
```http
POST /api/users/123234345/workouts

{
  "workoutName": "Monday Morning Workout",
  "username": "Arnold Schwarzenegger",
   "oauthId": "1154006894960655",
  "exercises": [
    {
      "exerciseName": "Bench Press",
      "sets": [
        {
          "setNumber": 1,
          "reps": 8,
          "weight": 70,
          "rpe": 8,
          "isDone": true
        },
        {
          "setNumber": 2,
          "reps": 8,
          "weight": 70,
          "rpe": 9,
          "isDone": true
        }
      ]
    },
    {
      "exerciseName": "RDL",
      "sets": [
        {
          "setNumber": 1,
          "reps": 15,
          "weight": 90,
          "rpe": 6,
          "isDone": false
        },
        {
          "setNumber": 2,
          "reps": 15,
          "weight": 90,
          "rpe": 7,
          "isDone": false
        }
      ]
    }
  ]
}
```

